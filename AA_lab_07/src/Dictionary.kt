import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class Dictionary
{
    val dictionary: MutableMap<Int, String> = mutableMapOf()

    fun fullByFile(filePath: String)
    {
        val reader = Files.newBufferedReader(Paths.get(filePath))
        val parser = CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))
        for (curRecord in parser)
            dictionary[curRecord[0].toInt()] = curRecord[1]

        if (!parser.isClosed)
            parser.close()
    }

    fun print()
    {
        for (i in dictionary)
            println(i)
    }
}