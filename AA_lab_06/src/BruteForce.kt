import Graph
import java.lang.Integer.MAX_VALUE
import java.util.*

class BruteForce
{
    fun bruteForceAlg(graph: Graph)
    {
        val permutations = getAllPermutations(graph.getVertecies())

        var minWayLength = MAX_VALUE
        var minWay: MutableList<Int> = mutableListOf()
        for (curWay in permutations.filter { it[0] == 0 })
        {
            curWay.add(0)
            val curWayLength = graph.getWayLength(curWay)
            if (curWayLength in 1 until minWayLength)
            {
                minWayLength = curWayLength
                minWay = curWay
            }
        }

        println("Min way length is: $minWayLength")
        println("Min way is: $minWay")
    }

    private fun getAllPermutations(unusedDots: MutableList<Int>,
                      result : MutableList<MutableList<Int>>? = null,
                      curWay : MutableList<Int>? = null) : MutableList<MutableList<Int>>
    {
        var ret = result
        if (ret == null)
            ret = mutableListOf()

        if (unusedDots.size == 0)
        {
            ret.add(curWay!!)
            return ret
        }

        for (i in 0 until unusedDots.size)
        {
            val newUnusedDots = unusedDots.toMutableList()
            newUnusedDots.removeAt(i)
            var newWay : MutableList<Int> = mutableListOf()
            if (curWay != null)
                newWay = curWay.toMutableList()
            newWay.add(unusedDots[i])

            getAllPermutations(newUnusedDots, ret, newWay)
        }
        return ret
    }
}