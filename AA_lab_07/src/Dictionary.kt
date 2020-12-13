import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class Dictionary
{
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

    fun print()
    {
        for (i in dictionary)
            println(i)
    }
}