import Graph
import BruteForce
import Colony
import kotlin.random.Random
import kotlin.system.measureNanoTime

fun main()
{
    val graph = Graph(4)
    graph.generate()

    graph.print()

    println("By the Brute Force Algorithm:")
//    var duration = measureNanoTime {
    BruteForce().bruteForceAlg(graph)
//    }
//    println("${duration.toInt()}")

//    for (i in doubleArrayOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0))
//    {
//    duration = measureNanoTime {
//        Colony(graph).antAlg(50, 0.5, 0.5, 0.5, 2.0)
//    }
//    println("${duration.toInt()}")

    val ans2 = Colony(graph).antAlg(50, 0.5, 0.5, 0.5, 2.0)
    println("By the Ant Algorithm:")
    println("Min way is: $ans2")
//    }

    return
}