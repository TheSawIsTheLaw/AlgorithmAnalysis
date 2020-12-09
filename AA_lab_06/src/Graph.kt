import kotlin.random.Random
import kotlin.Int as Int

val randomStart = 0.1
val randomEnd = 30.0

class Graph(size_ : Int)
{
    private var size = size_
    private var adjacencyMatrix = Array(size) { DoubleArray(size) }

    fun setWay(from : Int, to : Int, length : Double)
    {
        adjacencyMatrix[from][to] = length
    }

    fun getWay(from : Int, to : Int) : Double
    {
        return adjacencyMatrix[from][to]
    }

    fun getSize() : Int
    {
        return size
    }

    fun generate()
    {
        for (i in 0 until size)
            for (j in 0 until size)
                this.setWay(i, j, Random.nextDouble(randomStart, randomEnd))
    }

    fun print()
    {
        for (i in 0 until size)
        {
            for (j in 0 until size)
                print("%15f ".format(getWay(i, j)))
            print('\n')
        }
    }
}