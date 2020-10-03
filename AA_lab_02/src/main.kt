import kotlin.reflect.typeOf

//fun printOutMatrix(matrix: Array<IntArray>)
//{
//    for (i in 0..matrix.size())
//}

fun printOutMatrix(matrix: Array<IntArray>)
{
    print("\n") 
    for (i in matrix.indices)
    {
        for (j in matrix[i].indices)
            print("%3d".format(matrix[i][j]))
        print("\n")
    }
}

fun matricesMult(fMatrix: Array<IntArray>, sMatrix: Array<IntArray>) : Array<IntArray>
{
    val product = Array(fMatrix.size) { IntArray(sMatrix[0].size) }
    for (i in fMatrix.indices)
        for (j in sMatrix[0].indices)
            for (k in fMatrix[0].indices)
                product[i][j] += fMatrix[i][k] * sMatrix[k][j]
    return product
}

fun main()
{
    val firstMatrix = arrayOf(intArrayOf(3, -2, 5), intArrayOf(3, 0, 4))
    val secondMatrix = arrayOf(intArrayOf(1, 1, 1), intArrayOf(3, 3, 3), intArrayOf(2, 2, 2))
    printOutMatrix(firstMatrix)
    printOutMatrix(secondMatrix)

    println("Result of multiplication")
    val prod = matricesMult(firstMatrix, secondMatrix);
    printOutMatrix(prod)
}