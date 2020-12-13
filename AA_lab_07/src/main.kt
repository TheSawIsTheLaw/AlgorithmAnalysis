import kotlin.system.measureNanoTime
import kotlin.time.measureTimedValue

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

//    println("Введите ключ для поиска:")
//    val key = readLine()!!.toInt()
//
//    println()
//    println("Результат поиска полным перебором: ${dict.getValueByBrutForce(key)}")
//
//    println("Результат бинарного поиска: ${dict.getValueByBinarySearch(key)}")
//
//    println("Результат поиска с испоьзованием сегментирования: ${dict.getValueBySegmentedAndBinaryModified(key)}")

    var time : Long = 0

    var timeList : MutableList<Long> = mutableListOf()
    dict.getValueByBrutForce(0)
    for (i in 0 until 1000) {
        time = measureNanoTime {
            dict.getValueByBrutForce(i)
        }
        timeList.add(time)
    }
    println()
    println("Brut")
    println("Max: ${timeList.maxByOrNull { it }}")
    println("Min: ${timeList.minByOrNull { it }}")
    println("Average: ${timeList.average()}")

    timeList = mutableListOf()
    dict.getValueByBinarySearch(0)
    for (i in 0 until 1000) {
        time = measureNanoTime {
            dict.getValueByBinarySearch(i)
        }
        timeList.add(time)
    }
    println()
    println("Binary")
    println("Max: ${timeList.maxByOrNull { it }}")
    println("Min: ${timeList.minByOrNull { it }}")
    println("Average: ${timeList.average()}")

    timeList = mutableListOf()
    dict.getValueBySegmentedAndBinaryModified(0)
    for (i in 0 until 1000) {
        time = measureNanoTime {
            dict.getValueBySegmentedAndBinaryModified(i)
        }
        timeList.add(time)
    }
    println()
    println("Segmented")
    println("Max: ${timeList.maxByOrNull { it }}")
    println("Min: ${timeList.minByOrNull { it }}")
    println("Average: ${timeList.average()}")

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