import os
import random
import strutils
import sequtils

import times

var NUM_ELEMS = parseInt(paramStr(1))

randomize()

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
    var computedCols = colsComp(sMat)

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
    var prod = winogradMult(fMat, sMat)
    var timeEnd = now()
    echo "Time: ", timeEnd - timeStart

main()