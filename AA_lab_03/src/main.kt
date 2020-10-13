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
        var j = i;
        while (j > 0 && arr[j - 1] > arr[j])
        {
            arr[j - 1] = arr[j].also { arr[j] = arr[j - 1] }
            j--
        }
        i++
    }
}

fun selectionSort(arr: IntArray)
{
    var i = 0;

    while (i < arr.size - 2)
    {
        var j = i + 1
        while (j < arr.size - 1)
        {
            if (arr[i] > arr[j])
                arr[i] = arr[j].also { arr[j] = arr[i] }
            j++
        }
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
        arr[i] = nextInt();
}

@kotlin.time.ExperimentalTime
fun main()
{
    var arr1: IntArray = IntArray(5000)
    setRandom(arr1)

    var duration = measureTimedValue {
        selectionSort(arr1)
    }
    print(duration)
}