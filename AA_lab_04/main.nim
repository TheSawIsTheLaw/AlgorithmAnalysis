import strformat
import random
import strutils
import sequtils

type
  Matrix[W, H: static[int]] =
    array[1..W, array[1..H, int]]

proc printMat(mat : seq[seq[int]])=
    for i in 0..mat.len - 1:
        for j in 0..mat[1].len - 1:
            stdout.write fmt"{mat[i][j]} "
        echo ""

proc randMat(mat : var Matrix)=
    for i in low(mat)..high(mat):
        for j in low(mat[1])..high(mat[1]):
            mat[i][j] = rand(-20..20)

proc rowsComp(matrix : seq[seq[int]]) : seq[int]=
    var computedRows = newSeq[int](matrix.len)

    for i in 0..matrix.len - 1:
        var j = 0
        while j < matrix[1].len - 1:
            computedRows[j] += matrix[i][j] * matrix[i][j + 1]
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

proc main()=
    var fMat = getMat()

    echo "\nGot matrix:"
    printMat(fMat)
    echo ""

    var sMat = getMat()

    echo "\nGot matrix:"
    printMat(sMat)
    echo ""

    var prod = winogradMult(fMat, sMat)

    stdout.write "Result:\n"
    printMat(prod)

main()