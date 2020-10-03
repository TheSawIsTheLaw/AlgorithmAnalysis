import kotlin.reflect.typeOf

//fun printOutMatrix(matrix: Array<IntArray>)
//{
//    for (i in 0..matrix.size())
//}

fun printOutMatrix(matrix: Array<IntArray>)
{
    for (i in matrix.indices)
    {
        for (j in matrix[i].indices)
            print("%3d".format(matrix[i][j]))
        print("\n")
    }
}

fun main()
{
    val firstMatrix = arrayOf(intArrayOf(3, -2, 5), intArrayOf(3, 0, 4))
    printOutMatrix(firstMatrix)

}