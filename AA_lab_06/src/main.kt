import Graph

fun main()
{
    val graph = Graph(5)
    graph.generate()

    graph.print()

    val list = graph.getLinkedVertices(0)
    print(list)

    return
}