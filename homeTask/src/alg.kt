import kotlin.math.round

fun DDALine(screen : Array<Array<Triple<Int, Int, Int>>>,
            color : Triple<Int, Int, Int>,
            xStart : Int, yStart : Int,
            xEnd : Int, yEnd : Int) {
    if (xStart == xEnd && yStart == yEnd)                           // 1
    {
        screen[xStart][yStart] = color                              // 2
        return
    }

    var deltaX = (xEnd - xStart).toFloat()                          // 3
    var deltaY = (yEnd- yStart).toFloat()                           // 4

    var trX = kotlin.math.abs(deltaX)                               // 5
    var trY = kotlin.math.abs(deltaY)                               // 6

    var length : Int
    if (trX > trY)                                                  // 7
    {
        length = trX.toInt()                                        // 8
    }
    else
    {
        length = trY.toInt()                                        // 9
    }

    deltaX /= length                                                // 10
    deltaY /= length                                                // 11

    var curX = xStart.toFloat()                                     // 12
    var curY = yStart.toFloat()                                     // 13

    for (i in 0 until length)                                       // 14
    {
        screen[round(curX).toInt()][round(curY).toInt()] = color    // 15
        curX += deltaX                                              // 16
        curY += deltaY                                              // 17
    }
}

fun showScreen(screen : Array<Array<Triple<Int, Int, Int>>>)
{
    for (i in screen)
    {
        for (j in i)
            print("%d ".format(if (j.first == 0) 0 else 1))
        print('\n')
    }
}

fun main()
{
    var screen = Array(50) { Array(50) { Triple(255, 255, 255) } }
    DDALine(screen, Triple(0, 0, 0), 0, 0, 50, 50)
    showScreen(screen)
}