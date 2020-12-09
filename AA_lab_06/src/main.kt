import Graph
import BruteForce

fun main()
{
    val graph = Graph(3)
    graph.generate()

    graph.print()

    val answer = BruteForce().bruteForceAlg(graph)

    return
}