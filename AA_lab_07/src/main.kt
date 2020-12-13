const val directoryToCopySCV = "src/dataGenerator/dictionary.csv"

fun main()
{
    val dict = Dictionary()

    dict.fullByFile(directoryToCopySCV)
    if (dict.dictionary.isEmpty())
        return
    dict.print()

    println(dict.getValueByBrutForce(2))
    println(dict.getValueByBrutForce(6666))

    dict.getValueByBinarySearch(2)

    println(dict.getValueByBinarySearch(555))
    println(dict.getValueByBinarySearch(6666))
}