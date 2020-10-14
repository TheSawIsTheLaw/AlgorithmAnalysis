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
    var i = 2;
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

@kotlin.time.ExperimentalTime
fun main()
{
    var arr1: IntArray = IntArray(10)
    setRandom(arr1)

//    var duration = measureTimedValue {
//        selectionSort(arr1)
//    }
//    print(duration)

    insertionSort(arr1)
    printIntArray(arr1)
}