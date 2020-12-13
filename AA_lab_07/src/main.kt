const val directoryToCopySCV = "src/dataGenerator/dictionary.csv"

fun main()
{
    val dict = Dictionary()

    dict.fullByFile(directoryToCopySCV)
    if (dict.isEmpty())
        return
    dict.print()

    println("For brutForce")
    println(dict.getValueByBrutForce(460))
    println(dict.getValueByBrutForce(6666))

    println("For binarySearch")
    dict.sortForBinarySearch()
    dict.getValueByBinarySearch(2)

    println(dict.getValueByBinarySearch(800))
    println(dict.getValueByBinarySearch(6666))

    println("For modified segmented dictionary")
    dict.createSegmentedDictionary()
    println(dict.getValueBySegmentedAndBinaryModified(4))
    println(dict.getValueBySegmentedAndBinaryModified(460))
    println(dict.getValueBySegmentedAndBinaryModified(800))
    println(dict.getValueBySegmentedAndBinaryModified(-1))
}