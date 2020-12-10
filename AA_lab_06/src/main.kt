import Graph
import BruteForce
import Colony

fun main()
{
    val graph = Graph(10)
    graph.generate()

    graph.print()

    val answer = BruteForce().bruteForceAlg(graph)

    val ans2 = Colony(graph).antAlg(20, 4.0, 6.0, 0.6, 5.0)
    println("Oh shit: ${ans2}")

    return
}