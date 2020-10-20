import strformat
import random

type
  Matrix[W, H: static[int]] =
    array[1..W, array[1..H, int]]

proc printMat(mat : Matrix)=
    for i in low(mat)..high(mat):
        for j in low(mat[1])..high(mat):
            stdout.write fmt"{mat[i][j]} "
        echo ""

proc randMat(mat : var Matrix)=
    for i in low(mat)..high(mat):
        for j in low(mat[1])..high(mat[1]):
            mat[i][j] = rand(-20..20)

proc main()=
    var firstMat : Matrix[3, 3]

    randMat(firstMat)
    printMat(firstMat)

main()