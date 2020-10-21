import strformat
import random
import strutils
import sequtils

import locks

const THREADS = 4

var L : Lock
initLock(L)

proc printMat(mat : seq[seq[int]])=
    for i in 0..mat.len - 1:
        for j in 0..mat[1].len - 1:
            stdout.write fmt"{mat[i][j]:5} "
        echo ""

proc rowsComp(matrix : seq[seq[int]]) : seq[int]=
    var computedRows = newSeq[int](matrix.len)

    for i in 0..matrix.len - 1:
        var j = 0
        while j < matrix[0].len - 1:
            computedRows[i] += matrix[i][j] * matrix[i][j + 1]
            j += 2

    return computedRows

proc colsComp(matrix : seq[seq[int]]) : seq[int]=
    var computedCols = newSeq[int](matrix[0].len)

    var i = 0
    while i < matrix.len - 1:
        for j in 0..matrix[0].len - 1:
            computedCols[j] += matrix[i][j] * matrix[i + 1][j]
        i += 2

    return computedCols

proc winogradMult(fMat : seq[seq[int]], sMat : seq[seq[int]]) : seq[seq[int]]=
    if (fMat[0].len != sMat.len):
        return

    var computedRows = rowsComp(fMat)
    echo "Truly: ", computedRows
    var computedCols = colsComp(sMat)
    echo "Truly: ", computedCols

    var product = newSeqWith(fMat.len, newSeq[int](sMat[0].len))
    for i in 0..product.len - 1:
        for j in 0..product[0].len - 1:
            product[i][j] += -computedRows[i] - computedCols[j]

            var k = 0
            while k < sMat.len - 1:
                product[i][j] += (fMat[i][k] + sMat[k + 1][j]) * (fMat[i][k + 1] + sMat[k][j])
                k += 2

    if sMat.len %% 2 != 0:
        var curK = sMat.len - 1
        for i in 0..product.len - 1:
            for j in 0..product[0].len - 1:
                product[i][j] += fMat[i][curK] * sMat[curK][j]

    return product

proc getRows() : int=
    stdout.write "Rows: "
    return parseInt(readline(stdin))

proc getCols() : int=
    stdout.write "Cols: "
    return parseInt(readline(stdin))

proc getMat() : seq[seq[int]]=
    var rows = getRows()
    var cols = getCols()

    var mat = newSeqWith(rows, newSeq[int](cols))

    var inpStr = ""

    for i in 0..rows - 1:
        inpStr.add(readline(stdin))
        var temp = inpStr.split(' ')
        var j = 0
        for k in temp:
            try:
                mat[i][j] = k.parseInt
            except:
                discard
            finally:
                j += 1
                if j == cols:
                    break
        inpStr = ""

    return mat

proc rowsCompThreadFunc(info: tuple[startOfInterval, endOfInterval : int,
                                    computedRows : ptr seq[int],
                                    matrix : seq[seq[int]]]) {.thread.}=
    acquire(L)
    for i in info.startOfInterval..info.endOfInterval - 1:
        var j = 0
        while j < info.matrix[0].len - 1:
            info.computedRows[i] += info.matrix[i][j] * info.matrix[i][j + 1]
            j += 2
    release(L)

proc rowsCompParallel(matrix : seq[seq[int]]) : seq[int]=
    var compRows = newSeq[int](matrix.len)
    var compRowsPtr = addr compRows
    var thr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval: int, computedRows : ptr seq[int], matrix : seq[seq[int]]]]]
    var size = (matrix.len / THREADS).int
    for i in 0..thr.len - 2:
        createThread(thr[i], rowsCompThreadFunc, (i * size, (i + 1) * size, compRowsPtr, matrix))

    createThread(thr[thr.len - 1], rowsCompThreadFunc, ((thr.len - 1) * size, matrix.len, compRowsPtr, matrix))

    joinThreads(thr)
    return compRows

proc colsCompThreadFunc(info : tuple[startOfInterval, endOfInterval : int,
                        computedCols : ptr seq[int],
                        matrix : seq[seq[int]]]) {.thread.}=
    acquire(L)
    var i = 0
    while i < info.matrix.len - 1:
        for j in info.startOfInterval..info.endOfInterval - 1:
            info.computedCols[j] += info.matrix[i][j] * info.matrix[i + 1][j]
        i += 2
    release(L)

proc colsCompParallel(matrix : seq[seq[int]]) : seq[int]=
    var compCols = newSeq[int](matrix[0].len)
    var compColsPtr = addr compCols
    var thr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval: int, computedCols : ptr seq[int], matrix : seq[seq[int]]]]]
    var size = (matrix[0].len / THREADS).int
    for i in 0..thr.len - 2:
        createThread(thr[i], colsCompThreadFunc, (i * size, (i + 1) * size, compColsPtr, matrix))

    createThread(thr[thr.len - 1], colsCompThreadFunc, ((thr.len - 1) * size, matrix[0].len, compColsPtr, matrix))

    joinThreads(thr)
    return compCols


proc winogradMultParallel(fMat : seq[seq[int]], sMat : seq[seq[int]]) : seq[seq[int]]=
    if (fMat[0].len != sMat.len):
        return

    var computedRows = rowsCompParallel(fMat)
    echo computedRows
    var computedCols = colsCOmpParallel(sMat)
    echo computedCols

    return fMat

proc setRandomMat() : seq[seq[int]]=
    var matrix = newSeqWith(10, newSeq[int](10))
    for i in 0..matrix.len - 1:
        for j in 0..matrix.len - 1:
            matrix[i][j] = random(20)

    return matrix

proc main()=
    var fMat = setRandomMat()
    echo "\nGot matrix:"
    printMat(fMat)
    echo ""

    var sMat = setRandomMat()
    echo "\nGot matrix:"
    printMat(sMat)
    echo ""

    var prod = winogradMultParallel(fMat, sMat)
    var prod2 = winogradMult(fMat, sMat)

#    stdout.write "Result:\n"
#    printMat(prod)

main()