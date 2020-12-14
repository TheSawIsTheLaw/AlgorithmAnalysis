import kotlin.jvm.functions.FunctionN
import kotlin.system.measureNanoTime
import kotlin.time.measureTimedValue

const val directoryToCopySCV = "src/dataGenerator/dictionary2000.csv"

fun main()
{
    val dict = Dictionary()

    dict.fullByFile(directoryToCopySCV)
    if (dict.isEmpty())
        return
//    dict.print()

//    println("Введите ключ для поиска:")
//    val key = readLine()!!.toInt()
//
//    println()
//    println("Результат поиска полным перебором: ${dict.getValueByBrutForce(key)}")
//
//    println("Результат бинарного поиска: ${dict.getValueByBinarySearch(key)}")
//
//    println("Результат поиска с испоьзованием сегментирования: ${dict.getValueBySegmentedAndBinaryModified(key)}")

    var time : Long

    var averList: MutableList<Double> = mutableListOf()
    var maxList: MutableList<Long> = mutableListOf()
    var minList: MutableList<Long> = mutableListOf()

    var timeList : MutableList<Long>

    for (z in 0 until 10)
    {
        timeList = mutableListOf()
        for (i in 0..dict.size()) {
            time = measureNanoTime {
                dict.getValueByBrutForce(i)
            }
            timeList.add(time)
        }
        averList.add(timeList.average())
        maxList.add(timeList.maxByOrNull { it }!!)
        minList.add(timeList.minByOrNull { it }!!)
    }
    println()
    println("Brut")
    println("Average: ${averList.average()}")
    println("Max: ${maxList.maxByOrNull { it }}")
    println("Min: ${minList.minByOrNull { it }}")

    dict.sortForBinarySearch()

    averList = mutableListOf()
    maxList = mutableListOf()
    minList = mutableListOf()

    for (z in 0 until 10)
    {
        timeList = mutableListOf()
        for (i in 0..dict.size()) {
            time = measureNanoTime {
                dict.getValueByBinarySearch(i)
            }
            timeList.add(time)
        }
        averList.add(timeList.average())
        maxList.add(timeList.maxByOrNull { it }!!)
        minList.add(timeList.minByOrNull { it }!!)
    }
    println()
    println("Binary")
    println("Average: ${averList.average()}")
    println("Max: ${maxList.maxByOrNull { it }}")
    println("Min: ${minList.minByOrNull { it }}")

    dict.createSegmentedDictionary()

    averList = mutableListOf()
    maxList = mutableListOf()
    minList = mutableListOf()

    for (z in 0 until 10)
    {
        timeList = mutableListOf()
        for (i in 0..dict.size()) {
            time = measureNanoTime {
                dict.getValueByBinarySearch(i)
            }
            timeList.add(time)
        }
        averList.add(timeList.average())
        maxList.add(timeList.maxByOrNull { it }!!)
        minList.add(timeList.minByOrNull { it }!!)
    }
    println()
    println("Modified")
    println("Average: ${averList.average()}")
    println("Max: ${maxList.maxByOrNull { it }}")
    println("Min: ${minList.minByOrNull { it }}")

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