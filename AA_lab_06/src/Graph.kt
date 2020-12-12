import kotlin.random.Random
import kotlin.Int as Int

const val randomStart = 1
const val randomEnd = 3

class Graph(size_ : Int)
{
    private var size = size_
    private var adjacencyMatrix = Array(size) { IntArray(size) }

    fun setWay(from : Int, to : Int, length : Int)
    {
        adjacencyMatrix[from][to] = length
        adjacencyMatrix[to][from] = length
    }

    fun getWay(from : Int, to : Int) : Int
    {
        return adjacencyMatrix[from][to]
    }

    fun getWayLength(way : MutableList<Int>) : Int
    {
        var length = 0
        for (i in 0 until way.size - 1)
        {
            val curLength = getWay(way[i], way[i + 1])
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

//        adjacencyMatrix[0] = intArrayOf(0, 3, 14, 2, 22, 20, 2, 14, 18, 23)
//        adjacencyMatrix[1] = intArrayOf(3,   0,  12,  12,   8,  24,  19,   4,  20,  12)
//        adjacencyMatrix[2] = intArrayOf(14, 12,   0,  23,   8,   3,  21,  16,   6,   5)
//        adjacencyMatrix[3] = intArrayOf(2,  12,  23,   0,  22,  19,   1,  13,  15,   8)
//        adjacencyMatrix[4] = intArrayOf(22,  8,   8,  22,   0,   8,   2,   8,  11,   7)
//        adjacencyMatrix[5] = intArrayOf(20, 24,   3,  19,   8,   0,  15,  12,  18,   9)
//        adjacencyMatrix[6] = intArrayOf(2,  19,  21,   1,   2,  15,   0,  13,  18,  21)
//        adjacencyMatrix[7] = intArrayOf(14,  4,  16,  13,   8,  12,  13,   0,   7,  23)
//        adjacencyMatrix[8] = intArrayOf(18, 20,   6,  15,  11,  18,  18,   7,   0,  19)
//        adjacencyMatrix[9] = intArrayOf(23, 12,   5,   8,   7,   9,  21,  23,  19,   0)

//        adjacencyMatrix[0] = intArrayOf(  0, 242, 306,  590, 364,  379, 1202,  344,  505, 560)
//        adjacencyMatrix[1] = intArrayOf( 242,   0, 465, 1385, 853,  853,  878,  324,  173, 1006)
//        adjacencyMatrix[2] = intArrayOf( 306, 465,   0, 1418, 636, 1036,  553, 1463,  926, 551)
//        adjacencyMatrix[3] = intArrayOf( 590,1385,1418,    0,1198,  828,   11, 1214,    5, 552)
//        adjacencyMatrix[4] = intArrayOf( 364, 853, 636, 1198,   0, 1152, 1282,  807, 1494, 821)
//        adjacencyMatrix[5] = intArrayOf( 379, 853,1036,  828,1152,    0,  753,  185, 1440, 1200)
//        adjacencyMatrix[6] = intArrayOf(1202, 878, 553,   11,1282,  753,    0, 1228,  967, 308)
//        adjacencyMatrix[7] = intArrayOf( 344, 324,1463, 1214, 807,  185, 1228,    0, 1140, 1450)
//        adjacencyMatrix[8] = intArrayOf( 505, 173, 926,    5,1494, 1440,  967, 1140,    0, 533)
//        adjacencyMatrix[9] = intArrayOf( 560,1006, 551,  552, 821, 1200,  308, 1450,  533,   0)
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

    fun get(i : Int, j : Int) : Double
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