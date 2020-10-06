import kotlin.random.Random
import kotlin.system.measureNanoTime

fun printOutMatrix(matrix: Array<IntArray>)
{
    for (i in matrix.indices)
    {
        for (j in matrix[i].indices)
            print("%6d".format(matrix[i][j]))
        print("\n")
    }
}

fun matricesMult(fMatrix: Array<IntArray>, sMatrix: Array<IntArray>) : Array<IntArray>
{
    if (fMatrix[0].size != sMatrix.size)
        return emptyArray()

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
                product[i][j] = product[i][j] + (fMatrix[i][k * 2] + sMatrix[k * 2 + 1][j]) * (fMatrix[i][k * 2 + 1] + sMatrix[k * 2][j])
        }

    if (sMatrix.size % 2 != 0)
    {
        for (i in product.indices)
            for (j in product[0].indices)
                product[i][j] = product[i][j] + fMatrix[i][sMatrix.size - 1] * sMatrix[sMatrix.size - 1][j]
    }

    return product
}

fun rowsComputationModified(matrix: Array<IntArray>) : IntArray
{
    val computedRows = IntArray(matrix.size)
    for (i in matrix.indices)
    {
        var j = 0
        while (j < matrix[0].size - 1)
        {
            computedRows[i] += matrix[i][j] * matrix[i][j + 1]
            j += 2
        }
    }

    return computedRows
}

fun colsComputationModified(matrix: Array<IntArray>) : IntArray
{
    val computedCols = IntArray(matrix[0].size)

    var i = 0
   while (i < matrix.size - 1)
    {
        for (j in matrix[0].indices)
            computedCols[j] += matrix[i][j] * matrix[i + 1][j]
        i += 2
    }

    return computedCols
}

fun WinogradMultiplicationModified(fMatrix: Array<IntArray>, sMatrix: Array<IntArray>) : Array<IntArray>
{
    if (fMatrix[0].size != sMatrix.size)
        return emptyArray()

    val computedRows = rowsComputationModified(fMatrix)
    val computedCols = colsComputationModified(sMatrix)

    val product = Array(fMatrix.size) { IntArray(sMatrix[0].size) }
    for (i in product.indices)
        for (j in product[0].indices)
        {
            product[i][j] = -computedRows[i] - computedCols[j]

            var k = 0
            while (k < sMatrix.size - 1)
            {
                product[i][j] += (fMatrix[i][k] + sMatrix[k + 1][j]) * (fMatrix[i][k + 1] + sMatrix[k][j])
                k += 2
            }

//            for (k in 0 until (sMatrix.size - 1) step 2)
//                product[i][j] += (fMatrix[i][k] + sMatrix[k + 1][j]) * (fMatrix[i][k + 1] + sMatrix[k][j])
        }

    if (sMatrix.size % 2 != 0)
    {
        val curK = sMatrix.size - 1
        for (i in product.indices)
            for (j in product[0].indices)
                product[i][j] = product[i][j] + fMatrix[i][curK] * sMatrix[curK][j]
    }

    return product
}

fun timeResearch(fMatrix: Array<IntArray>, sMatrix: Array<IntArray>,
                 func : (fMatrix: Array<IntArray>, sMatrix: Array<IntArray>) -> Array<IntArray>)
{
    func(fMatrix, sMatrix)
    var time : Long = measureNanoTime {
        func(fMatrix, sMatrix)
    }

    println("Time research result is: $time")
}


fun makeMat(rowSize: Int, colSize: Int) : Array<IntArray>
{
    return Array(rowSize) { IntArray(colSize) }
}


fun fullMatRandomly(matrix: Array<IntArray>)
{
    for (i in matrix.indices)
        for (j in matrix[0].indices)
            matrix[i][j] = Random.nextInt(-20, 20)
}


fun main()
{
    val firstMatrix = makeMat(100, 100)
    fullMatRandomly(firstMatrix)
    val secondMatrix = makeMat(100, 100)
    fullMatRandomly(secondMatrix)

    println("First matrix is:")
//    printOutMatrix(firstMatrix)

    println("Second matrix is:")
//    printOutMatrix(secondMatrix)

    println("\n\nResult of multiplication in classic:")
    val prod = matricesMult(firstMatrix, secondMatrix);

    timeResearch(firstMatrix, secondMatrix, ::matricesMult)

//    printOutMatrix(prod)

    println("\n\nResult of multiplication in Winograd")

    val newProd = WinogradMultiplication(firstMatrix, secondMatrix)

    timeResearch(firstMatrix, secondMatrix, ::WinogradMultiplication)

//    printOutMatrix(newProd)

    println("\n\nResult of multiplication in Upd Winograd")
    val newestProd = WinogradMultiplicationModified(firstMatrix, secondMatrix)

    timeResearch(firstMatrix, secondMatrix, ::WinogradMultiplicationModified)

//    printOutMatrix(newestProd)
}