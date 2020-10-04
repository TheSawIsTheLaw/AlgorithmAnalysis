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

fun rowsComputation(matrix: Array<IntArray>) : IntArray
{
    val computedRows = IntArray(matrix.size)

    for (i in matrix.indices)
        for (j in 0 until (matrix[0].size - 1) / 2)
            computedRows[i] = computedRows[i] + matrix[i][j * 2] * matrix[i][j * 2 + 1]

    return computedRows
}

fun colsComputation(matrix: Array<IntArray>) : IntArray
{
    val computedCols = IntArray(matrix[0].size)

    for (i in 0 until (matrix.size - 1) / 2)
        for (j in matrix[0].indices)
            computedCols[j] = computedCols[j] + matrix[i * 2][j] * matrix[i * 2 + 1][j]

    return computedCols
}

fun WinogradMultiplication(fMatrix: Array<IntArray>, sMatrix: Array<IntArray>) : Array<IntArray>
{
    if (fMatrix[0].size != sMatrix.size)
        return emptyArray()

    val computedRows = rowsComputation(fMatrix)
    val computedCols = colsComputation(sMatrix)

    val product = Array(fMatrix.size) { IntArray(sMatrix[0].size) }
    for (i in product.indices)
        for (j in product[0].indices)
        {
            product[i][j] = -computedRows[i] - computedCols[j]

            for (k in 0 until (sMatrix.size / 2))
                product[i][j] = product [i][j] + (fMatrix[i][k * 2] + sMatrix[k * 2 + 1][j]) * (fMatrix[i][k * 2 + 1] + sMatrix[k * 2][j])
        }

    if (sMatrix.size % 2 != 0)
    {
        for (i in product.indices)
            for (j in product[0].indices)
                product[i][j] = product[i][j] + fMatrix[i][sMatrix.size - 1] * sMatrix[sMatrix.size - 1][j]
    }

    return product
}

fun main()
{
    val firstMatrix = arrayOf(intArrayOf(3, -2, 5), intArrayOf(3, 0, 4))
    val secondMatrix = arrayOf(intArrayOf(1, 1, 1), intArrayOf(3, 3, 3), intArrayOf(2, 2, 2))
    printOutMatrix(firstMatrix)
    printOutMatrix(secondMatrix)

    println("\n\nResult of multiplication in classic:")
    val prod = matricesMult(firstMatrix, secondMatrix);
    printOutMatrix(prod)

    println("\n\nResult of multiplication in Winograd")
    val newProd = WinogradMultiplication(firstMatrix, secondMatrix)
    printOutMatrix(newProd)
}