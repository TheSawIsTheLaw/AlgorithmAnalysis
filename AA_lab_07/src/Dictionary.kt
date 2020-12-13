import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class Dictionary
{
    val NOT_FOUND = "NOT_FOUND"

    val dictionary: MutableList<Pair<Int, String>> = mutableListOf()

    fun fullByFile(filePath: String)
    {
        val reader = Files.newBufferedReader(Paths.get(filePath))
        val parser = CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))

        val shuffleList : MutableList<Pair<Int, String>> = mutableListOf()

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

    fun getValueByBinarySearch(key: Int) : String
    {
        dictionary.sortBy { it.first }
        var firstNode = 0
        var secondNode = dictionary.size - 1
        while (firstNode <= secondNode)
        {
            val middle = (firstNode + secondNode) / 2
            val curRecordID = dictionary[middle]

            when
            {
                curRecordID.first > key -> secondNode--
                curRecordID.first < key -> firstNode++
                else -> return "{ %d : %s }".format(key, curRecordID.second)
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