import Graph
import kotlin.math.pow
import kotlin.random.Random

class Colony(graph_: Graph)
{
    var graph = graph_

    class Ant
    {
        var way : MutableList<Int> = mutableListOf()
        var startVertex : Int = 0
        var visitedVerticies = BooleanArray(0)

        var graph = Graph(0)

        constructor(graph_: Graph, startVertex_ : Int)
        {
            way = mutableListOf(startVertex_)
            visitedVerticies = BooleanArray(graph_.getSize())
            startVertex = startVertex_
            visitedVerticies[startVertex_] = true

            graph = graph_
        }

        fun visitVertex(vertex : Int)
        {
            visitedVerticies[vertex] = true
            way.add(vertex)
        }

        fun getWayLength(way : MutableList<Int>): Int
        {
            return graph.getWayLength(way)
        }
    }

    val initValue = 0.1
    val startCity = 1

    fun antsInitialization() : MutableList<Ant>
    {
        val ants = mutableListOf<Ant>()
        for (i in 0 until graph.getSize())
            ants.add(Ant(graph, startCity))

        return ants
    }

    fun antAlg(days : Int, alpha : Double, beta : Double, rho : Double, q : Double) : Pair<MutableList<Int>, Int>
    {
        var minWay = Int.MAX_VALUE
        var min = mutableListOf<Int>()
        var pheromoneGraph = PheromoneGraph(graph.getSize())

        for (i in 0 until pheromoneGraph.getSize())
        {
            for (j in 0 until pheromoneGraph.getSize())
                pheromoneGraph.set(i, j, initValue)
        }

        for (day in 0 until days)
        {
            val ants = antsInitialization()

            for (i in 0 until graph.getSize())
            {
                val tempPheromoneGraph = PheromoneGraph(pheromoneGraph.getSize())

                for (curAntNum in 0 until ants.size)
                {
                    var curAnt = ants[curAntNum]
                    var sumChance = 0.0
                    var curVertex = curAnt.way.last()
//                    println("For ant $curAntNum curVertex = $curVertex")

                    for (vertId in 0 until graph.getSize())
                    {
                        if (!curAnt.visitedVerticies[vertId])
                            sumChance += pheromoneGraph.get(curVertex, vertId).pow(alpha) *
                                    (1.0 / graph.getWay(curVertex, vertId).toDouble()).pow(beta)
                    }

                    var coin = Random.nextDouble()
                    var curChoice = 0
                    while (coin > 0 && curChoice < curAnt.visitedVerticies.size)
                    {
                        if (!curAnt.visitedVerticies[curChoice])
                        {
                            var chance = pheromoneGraph.get(curVertex, curChoice).pow(alpha) * (1.0 / graph.getWay(curVertex, curChoice).toDouble()).pow(beta) / sumChance
                            coin -= chance
                        }
                        curChoice++
                    }
                    curChoice--

                    ants[curAntNum].visitVertex(curChoice)
                    tempPheromoneGraph.set(curVertex, curChoice, tempPheromoneGraph.get(curVertex, curChoice) + q / graph.getWay(curVertex, curChoice).toDouble())
                }

                for (k in 0 until graph.getSize())
                    for (j in 0 until graph.getSize())
                        pheromoneGraph.set(k, j, (1 - rho) * pheromoneGraph.get(k, j) + tempPheromoneGraph.get(k, j))
            }

            for (ant in ants)
            {
                ant.visitVertex(startCity)
                val cur = ant.graph.getWayLength(ant.way)
                if (cur < minWay)
                {
                    minWay = ant.graph.getWayLength(ant.way)
                    min = ant.way
                }
            }
        }

        return Pair(min, minWay)
    }
}