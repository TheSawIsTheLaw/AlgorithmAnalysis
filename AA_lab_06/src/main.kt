import Graph
import kotlin.random.Random

fun main()
{
    var graph = Graph(3)
    graph.generate()

    graph.print()

    return
}