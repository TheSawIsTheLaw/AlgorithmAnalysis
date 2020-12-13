const val directoryToCopySCV = "src/dataGenerator/dictionary.csv"

fun main()
{
    val dict = Dictionary()

    dict.fullByFile(directoryToCopySCV)
    if (dict.dictionary.isEmpty())
        return
    dict.print()
}