import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class Dictionary
{
    private val NOT_FOUND = "NOT_FOUND"

    private val dictionary: MutableList<Pair<Int, String>> = mutableListOf()
    private val segmentedDictionary: MutableList<Pair<Int, MutableList<Pair<Int, String>>>> = mutableListOf()

    private val secretFunc: (Int) -> (Int) = { it % 100 }

    fun isEmpty() : Boolean
    {
        return dictionary.isEmpty()
    }

    fun size() : Int
    {
        return dictionary.size
    }

    fun fullByFile(filePath: String)
    {
        val reader = Files.newBufferedReader(Paths.get(filePath))
        val parser = CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))

        for (curRecord in parser)
            dictionary.add(Pair(curRecord[0].toInt(), curRecord[1]))

        if (!parser.isClosed)
            parser.close()

        dictionary.shuffle()
    }

    fun getValueByBrutForce(key: Int) : String
    {
        for (curIndex in 0 until dictionary.size)
        {
            if (dictionary[curIndex].first == key)
                return "{ %d : %s }".format(key, dictionary[curIndex].second)
        }
        return NOT_FOUND
    }

    fun sortForBinarySearch(dictionary_: MutableList<Pair<Int, String>> = this.dictionary)
    {
        dictionary_.sortBy { it.first }
    }

    fun getValueByBinarySearch(key: Int, dictionary_: MutableList<Pair<Int, String>> = this.dictionary) : String
    {
        var firstNode = 0
        var secondNode = dictionary_.size - 1
        while (firstNode <= secondNode)
        {
            val middle = (firstNode + secondNode) / 2
            val curRecordID = dictionary_[middle]

            if (curRecordID.first > key)
                secondNode--
            else if (curRecordID.first < key)
                firstNode++
            else
                return "{ %d : %s }".format(key, curRecordID.second)

//            when
//            {
//                curRecordID.first > key -> secondNode--
//                curRecordID.first < key -> firstNode++
//                else -> return "{ %d : %s }".format(key, curRecordID.second)
//            }
        }

        return NOT_FOUND
    }

    fun createSegmentedDictionary()
    {
        for (i in 0 until 100)
            segmentedDictionary.add(Pair(i, dictionary.filter { secretFunc(it.first) == i }.toMutableList()))

        segmentedDictionary.forEach { segment -> segment.second.sortBy { it.first } }
        segmentedDictionary.sortBy { it.second.size }
    }

    fun getValueBySegmentedAndBinaryModified(key: Int) : String
    {
        if (segmentedDictionary.isEmpty())
            return "No segmented array."

        var firstNode = 0
        var secondNode = segmentedDictionary.size - 1
        while (firstNode <= secondNode)
        {
            val middle = (firstNode + secondNode) / 2
            val curSegment = segmentedDictionary[middle]

            when
            {
                curSegment.first > secretFunc(key) -> secondNode--
                curSegment.first < secretFunc(key) -> firstNode++
                else -> return getValueByBinarySearch(key, curSegment.second)
            }
        }

        return NOT_FOUND
    }

    fun print()
    {
        for (i in dictionary)
            println(i)
    }
}