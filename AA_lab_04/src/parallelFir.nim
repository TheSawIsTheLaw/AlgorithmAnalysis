import random
import os
import strutils
import sequtils

import times

var NUM_ELEMS = parseInt(paramStr(1))

const THREADS = 2

randomize()

proc rowsCompThreadFunc(info: tuple[startOfInterval, endOfInterval : int,
                                    computedRows : ptr seq[int],
                                    matrix : seq[seq[int]]])=
    for i in info.startOfInterval..info.endOfInterval - 1:
        var j = 0
        while j < info.matrix[0].len - 1:
            info.computedRows[i] += info.matrix[i][j] * info.matrix[i][j + 1]
            j += 2

proc rowsCompParallel(matrix : seq[seq[int]]) : seq[int]=
    var compRows = newSeq[int](matrix.len)
    var compRowsPtr = addr compRows
    var thr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval: int,
                                             computedRows : ptr seq[int],
                                             matrix : seq[seq[int]]]]]
    var size = (matrix.len / THREADS).int
    for i in 0..thr.len - 2:
        createThread(thr[i], rowsCompThreadFunc, (i * size, (i + 1) * size, compRowsPtr, matrix))

    createThread(thr[thr.len - 1], rowsCompThreadFunc, ((thr.len - 1) * size, matrix.len, compRowsPtr, matrix))

    joinThreads(thr)
    return compRows

proc colsCompThreadFunc(info : tuple[startOfInterval, endOfInterval : int,
                        computedCols : ptr seq[int],
                        matrix : seq[seq[int]]])=
    var i = 0
    while i < info.matrix.len - 1:
        for j in info.startOfInterval..info.endOfInterval - 1:
            info.computedCols[j] += info.matrix[i][j] * info.matrix[i + 1][j]
        i += 2


proc colsCompParallel(matrix : seq[seq[int]]) : seq[int]=
    var compCols = newSeq[int](matrix[0].len)
    var compColsPtr = addr compCols
    var thr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval: int,
                                             computedCols : ptr seq[int],
                                             matrix : seq[seq[int]]]]]
    var size = (matrix[0].len / THREADS).int
    for i in 0..thr.len - 2:
        createThread(thr[i], colsCompThreadFunc, (i * size, (i + 1) * size, compColsPtr, matrix))

    createThread(thr[thr.len - 1], colsCompThreadFunc, ((thr.len - 1) * size, matrix[0].len, compColsPtr, matrix))

    joinThreads(thr)
    return compCols


proc prepareThreadProd(info : tuple[startOfInterval, endOfInterval, row : int,
                                    product : ptr seq[seq[int]],
                                    computedRows, computedCols : seq[int]])=
    for j in info.startOfInterval..info.endOfInterval - 1:
        info.product[info.row][j] += -info.computedRows[info.row] - info.computedCols[j]

proc finThreadProd(info : tuple[startOfInterval, endOfInterval, row : int,
                                product : ptr seq[seq[int]],
                                fMat, sMat : seq[seq[int]]])=
    var k = 0
    for j in info.startOfInterval..info.endOfInterval - 1:
        k = 0
        while k < info.sMat.len - 1:
            info.product[info.row][j] += (info.fMat[info.row][k] + info.sMat[k + 1][j]) * (info.fMat[info.row][k + 1] + info.sMat[k][j])
            k += 2


proc multParallelFirst(fMat : seq[seq[int]], sMat : seq[seq[int]], computedRows, computedCols : seq[int]) : seq[seq[int]]=
    var product = newSeqWith(fMat.len, newSeq[int](sMat[0].len))

    var prodPtr = addr product
    var fThr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval, row : int,
                                        product : ptr seq[seq[int]],
                                        computedRows, computedCols : seq[int]]]]
    var size = (product[0].len / THREADS).int
    for i in 0..product.len - 1:
        for j in 0..fThr.len - 2:
            createThread(fThr[j], prepareThreadProd, (j * size, (j + 1) * size, i, prodPtr, computedRows, computedCols))
        createThread(fThr[fThr.len - 1], prepareThreadProd, ((fThr.len - 1) * size, product[0].len, i, prodPtr, computedRows, computedCols))
        joinThreads(fThr)

    var sThr : array[0..THREADS, Thread[tuple[startOfInterval, endOfInterval, row : int,
                                        product : ptr seq[seq[int]],
                                        fMat, sMat : seq[seq[int]]]]]
    for i in 0..product.len - 1:
        for j in 0..sThr.len - 2:
            createThread(sThr[j], finThreadProd, (j * size, (j + 1) * size, i, prodPtr, fMat, sMat))
        createThread(sThr[sThr.len - 1], finThreadProd, ((sThr.len - 1) * size, product[0].len, i, prodPtr, fMat, sMat))
        joinThreads(sThr)

    if sMat.len %% 2 != 0:
        var curK = sMat.len - 1
        for i in 0..product.len - 1:
            for j in 0..product[0].len - 1:
                product[i][j] += fMat[i][curK] * sMat[curK][j]

    return product


proc winogradMultParallelFirst(fMat : seq[seq[int]], sMat : seq[seq[int]]) : seq[seq[int]]=
    if (fMat[0].len != sMat.len):
        return

    var computedRows = rowsCompParallel(fMat)
    var computedCols = colsCompParallel(sMat)

    var product = multParallelFirst(fMat, sMat, computedRows, computedCols)

    return product

proc setRandomMat() : seq[seq[int]]=
    var matrix = newSeqWith(NUM_ELEMS, newSeq[int](NUM_ELEMS))
    for i in 0..matrix.len - 1:
        for j in 0..matrix.len - 1:
            matrix[i][j] = random(20)

    return matrix

proc main()=
    var fMat = setRandomMat()

    var sMat = setRandomMat()

    var timeStart = now()
    var prod = winogradMultParallelFirst(fMat, sMat)
    var timeEnd = now()
    echo (timeEnd - timeStart).nanoseconds
main()