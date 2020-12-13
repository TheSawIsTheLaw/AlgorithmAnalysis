const val directoryToCopySCV = "src/dataGenerator/dictionary.csv"

fun main()
{
    val dict = Dictionary()

    dict.fullByFile(directoryToCopySCV)
    if (dict.isEmpty())
        return
//    dict.print()

    dict.sortForBinarySearch()
    dict.createSegmentedDictionary()

    println("Введите ключ для поиска:")
    val key = readLine()!!.toInt()

    println()
    println("Результат поиска полным перебором: ${dict.getValueByBrutForce(key)}")

    println("Результат бинарного поиска: ${dict.getValueByBinarySearch(key)}")

    println("Результат поиска с испоьзованием сегментирования: ${dict.getValueBySegmentedAndBinaryModified(key)}")

//    println("For brutForce")
//    println(dict.getValueByBrutForce(460))
//    println(dict.getValueByBrutForce(6666))

//    println("For binarySearch")
//    dict.getValueByBinarySearch(2)

//    println(dict.getValueByBinarySearch(800))
//    println(dict.getValueByBinarySearch(6666))

//    println("For modified segmented dictionary")
//    println(dict.getValueBySegmentedAndBinaryModified(4))
//    println(dict.getValueBySegmentedAndBinaryModified(460))
//    println(dict.getValueBySegmentedAndBinaryModified(800))
//    println(dict.getValueBySegmentedAndBinaryModified(-1))
}