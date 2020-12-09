import Graph
import java.util.*

class BruteForce
{
    fun bruteForceAlg(graph: Graph) : MutableList<Pair<MutableList<Int>, Int>>
    {
        val bestWay = IntArray(graph.getSize())
        var bestWayLen = -1
        val emptyWay = mutableListOf<Int>()
        val queue : Queue<MutableList<Int>> = LinkedList<MutableList<Int>>()
        queue.add(mutableListOf(1, 0))

        while (!queue.isEmpty())
        {
            var currentWay = queue.peek()
            queue.remove()
            for (vertex in graph.getLinkedVertices(currentWay[currentWay.size - 1]))
        }
    }
}