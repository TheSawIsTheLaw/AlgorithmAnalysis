import Graph
import kotlin.random.Random

fun main()
{
    var graph = Graph(3)
    graph.generate()

    graph.print()

    val list = graph.getLinkedVertices(0)
    print(list)

    return
}