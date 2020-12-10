import kotlin.random.Random
import kotlin.Int as Int

const val randomStart = 1
const val randomEnd = 10

class Graph(size_ : Int)
{
    private var size = size_
    private var adjacencyMatrix = Array(size) { IntArray(size) }

    fun setWay(from : Int, to : Int, length : Int)
    {
        adjacencyMatrix[from][to] = length
        adjacencyMatrix[to][from] = length
    }

    private fun getWay(from : Int, to : Int) : Int
    {
        return adjacencyMatrix[from][to]
    }

    fun getWayLength(way : MutableList<Int>) : Int
    {
        var length = 0
        for (i in 0 until way.size - 1)
        {
            val curLength = getWay(way[i], way[i + 1])
            if (curLength == 0)
            {
                length = -1
                break
            }
            else
                length += curLength
        }
        return length
    }

    fun getSize() : Int
    {
        return size
    }

    fun generate()
    {
        for (i in 0 until size)
            for (j in 0 until i)
                setWay(i, j, Random.nextInt(randomStart, randomEnd))
    }

    fun getLinkedVertices(vertexNum : Int) : MutableList<Int>
    {
        val retList = mutableListOf<Int>()
        for (i in 0 until size)
            if (adjacencyMatrix[vertexNum][i] > 0)
                retList.add(i)

        return retList
    }

    fun getVertecies() : MutableList<Int>
    {
        val ret : MutableList<Int> = mutableListOf()
        for (i in 0 until size)
            ret.add(i)
        return ret
    }

    fun print()
    {
        for (i in 0 until size)
        {
            for (j in 0 until size)
                print("%3d ".format(getWay(i, j)))
            print('\n')
        }
    }
}

class PheromoneGraph(size_ : Int)
{
    private var size = size_
    private var adjacencyMatrix = Array(size) { DoubleArray(size) }

    fun set(i : Int, j : Int, value : Double)
    {
        adjacencyMatrix[i][j] = value
    }

    private fun get(i : Int, j : Int) : Double
    {
        return adjacencyMatrix[i][j]
    }

    fun getSize() : Int
    {
        return size
    }

    fun print()
    {
        for (i in 0 until size)
        {
            for (j in 0 until size)
                print("%5f ".format(get(i, j)))
            print('\n')
        }
    }
}