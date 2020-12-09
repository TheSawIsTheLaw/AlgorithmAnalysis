import kotlin.random.Random
import kotlin.Int as Int

val randomStart = 0
val randomEnd = 5

class Graph(size_ : Int)
{
    private var size = size_
    private var adjacencyMatrix = Array(size) { IntArray(size) }

    fun setWay(from : Int, to : Int, length : Int)
    {
        adjacencyMatrix[from][to] = length
    }

    fun getWay(from : Int, to : Int) : Int
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
                this.setWay(i, j, Random.nextInt(randomStart, randomEnd))
    }

    fun getLinkedVertices(vertexNum : Int) : MutableList<Int>
    {
        val retList = mutableListOf<Int>()
        for (i in 0 until size)
            if (adjacencyMatrix[vertexNum][i] > 0)
                retList.add(i)

        return retList
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