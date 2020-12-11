import Graph
import BruteForce
import Colony
import kotlin.random.Random

fun main()
{
    val graph = Graph(10)
    graph.generate()

    graph.print()

//    println("By the Brute Force Algorithm:")
//    BruteForce().bruteForceAlg(graph)

    for (i in doubleArrayOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0))
    {
        val ans2 = Colony(graph).antAlg(50, 1.0, 0.0, i, 2.0)
        println("By the Ant Algorithm:")
        println("Min way is: ${i} ${ans2}")
    }

    return
}