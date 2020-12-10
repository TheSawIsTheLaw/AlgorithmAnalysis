import Graph
import kotlin.random.Random

class Colony(graph_: Graph)
{
    class Parameters
    {
        var alpha : Double = 0.0
        var beta : Double = 0.0
        var p : Double = 0.0
        var q : Double = 0.0
        var startPheromone : Double = 0.0
        var times : Int = 0
    }

    class Result
    {
        var way : MutableList<Int> = mutableListOf()
        var dats : Int = 0
        var wayLength : Int = 0
    }

    private val startPh = 0.3

    private val graph : Graph = graph_
    private val pheromoneGraph : PheromoneGraph = PheromoneGraph(graph.getSize())
    private val parameters : Parameters = Parameters()

    fun simulation(parameters_ : Parameters, days : Int)
    {
        for (i in 0 until pheromoneGraph.getSize())
            for (j in 0 until pheromoneGraph.getSize())
                pheromoneGraph.set(i, j, startPh)
    }
}