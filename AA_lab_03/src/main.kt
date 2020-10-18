import java.util.*
import kotlin.random.Random.Default.nextInt
import kotlin.time.measureTimedValue

fun bubbleSort(arr: IntArray)
{
    var i = 0;
    while (i < arr.size - 1)
    {
        var j = 0;
        while (j < arr.size - 1 - i)
        {
            if (arr[j] > arr[j + 1])
                arr[j + 1] = arr[j].also { arr[j] = arr[j + 1] }
            j++
        }
        i++
    }
}

fun insertionSort(arr: IntArray)
{
    var i = 1;
    while (i < arr.size)
    {
        var min = arr[i]
        var j = i;
        while (j > 0 && arr[j - 1] > min)
        {
            arr[j] = arr[j - 1]
            j--
        }
        arr[j] = min
        i++
    }
}

fun selectionSort(arr: IntArray)
{
    var i = 0;
    while (i < arr.size - 1)
    {
        var j = i + 1
        var min = i
        while (j < arr.size)
        {
            if (arr[j] < arr[min])
                min = j
            j++
        }
        if (min != i)
            arr[i] = arr[min].also { arr[min] = arr[i] }
        i++
    }
}

fun printIntArray(arr: IntArray)
{
    for (i in arr.indices)
        print("%d ".format(arr[i]));
    print('\n')
}

fun setRandom(arr: IntArray)
{
    for (i in arr.indices)
        arr[i] = nextInt(0, 30);
}

fun getArr() : IntArray
{
    val input = Scanner(System.`in`)
    println("\nNumber of elements: ")
    val size = input.nextInt()

    if (size < 1)
        return IntArray(0)

    var outArr = IntArray(size)

    print("Array elements: ")

    for (i in outArr.indices)
        outArr[i] = input.nextInt()
    return outArr
}

fun printOutIntArray(arr : IntArray)
{
    for (i in arr.indices)
        print("${arr[i]} ")
    println()
}

fun setSorted(arr : IntArray)
{
    for (i in arr.indices)
        arr[i] = i
}

fun setUnsorted(arr : IntArray)
{
    for (i in arr.indices)
        arr[i] = arr.size - i
}

@kotlin.time.ExperimentalTime
fun main()
{
    var arr = IntArray(5000)
    setRandom(arr)
//    setSorted(arr)
//    setUnsorted(arr)

    var duration = measureTimedValue {
//        bubbleSort(arr)
//        insertionSort(arr)
        selectionSort(arr)
    }
    print(duration.duration.inNanoseconds.toInt())

//    insertionSort(arr1)
//    printIntArray(arr1)

//    var array = getArr()
//    var selectionArr = array.copyOf()
//    var bubbleArr = array.copyOf()
//    var insertionArr = array.copyOf()
//
//    bubbleSort(bubbleArr)
//    println("Ready in bubble sort:")
//    printOutIntArray(bubbleArr)
//
//    selectionSort(selectionArr)
//    println("Ready in selection sort:")
//    printOutIntArray(selectionArr)
//
//    insertionSort(insertionArr)
//    println("Ready in insertion sort:")
//    printOutIntArray(insertionArr)
}