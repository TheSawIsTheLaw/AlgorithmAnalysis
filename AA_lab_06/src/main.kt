import Graph
import BruteForce
import Colony

fun main()
{
    val graph = Graph(10)
    graph.generate()

    graph.print()

    println("By the Brute Force Algorithm:")
    BruteForce().bruteForceAlg(graph)

    val ans2 = Colony(graph).antAlg(300, 4.0, 6.0, 0.6, 5.0)
    println("By the Ant Algorithm:")
    println("Min way is: ${ans2}")

    return
}