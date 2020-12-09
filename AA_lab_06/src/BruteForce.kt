import Graph
import java.util.*

class BruteForce
{
    private fun lenWay(graph: Graph, way : MutableList<Int>) : Int
    {
        var len = 0
        for (i in 0 until way.size - 1)
            len += graph.getWay(way[i], way[i + 1])

        return len
    }

    fun bruteForceAlg(graph: Graph) : Pair<IntArray, Int>
    {
        var bestWay = IntArray(graph.getSize())
        var bestWayLen = -1
        val queue : Queue<MutableList<Int>> = LinkedList<MutableList<Int>>()
        queue.add(mutableListOf(1, 0))

        while (!queue.isEmpty())
        {
            var currentWay = queue.peek()
            queue.remove()
            for (vertex in graph.getLinkedVertices(currentWay[currentWay.size - 1]))
            {
                if (currentWay.find { it == vertex } == currentWay.last())
                {
                    val newWay = currentWay
                    newWay.add(vertex)
                    if (newWay.size == graph.getSize())
                    {
                        val len = lenWay(graph, newWay)
                        if (len < bestWayLen || bestWayLen > 0)
                        {
                            bestWayLen = len
                            bestWay = newWay.toIntArray()
                        }
                    }
                    else
                        queue.add(newWay)
                }
            }
        }
        return Pair(bestWay, bestWayLen)
    }
}