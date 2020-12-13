import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class Dictionary
{
    val NOT_FOUND = "NOT_FOUND"

    val firstSegmentCondition : (Int) -> (Boolean) = { key -> key <= dictionary.size / 4 }
    val secondSegmentCondition : (Int) -> (Boolean) = { key -> !firstSegmentCondition(key) && key < dictionary.size / 2 }
    val thirdSegmentCond : (Int) -> (Boolean) = { key -> key >= dictionary.size / 2 }

    val dictionary: MutableList<Pair<Int, String>> = mutableListOf()
    val segmentedDictionary: MutableList<MutableList<Pair<Int, String>>> = mutableListOf()

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

            when
            {
                curRecordID.first > key -> secondNode--
                curRecordID.first < key -> firstNode++
                else -> return "{ %d : %s }".format(key, curRecordID.second)
            }
        }

        return NOT_FOUND
    }

    fun createSegmentedDictionary()
    {
        segmentedDictionary.add(dictionary.filter { firstSegmentCondition(it.first) }.toMutableList())
        segmentedDictionary.add(dictionary.filter { secondSegmentCondition(it.first) }.toMutableList())
        segmentedDictionary.add(dictionary.filter { thirdSegmentCond(it.first) }.toMutableList())

        segmentedDictionary.forEach { segment -> segment.sortBy { it.first } }
    }

    fun getValueBySegmentedAndBinaryModified(key: Int) : String
    {
        if (segmentedDictionary.isEmpty())
            return "No segmented array."

        return when
        {
            firstSegmentCondition(key) -> getValueByBinarySearch(key, segmentedDictionary[0])
            secondSegmentCondition(key) -> getValueByBinarySearch(key, segmentedDictionary[1])
            else -> getValueByBinarySearch(key, segmentedDictionary[2])
        }
    }

    fun print()
    {
        for (i in dictionary)
            println(i)
    }
}