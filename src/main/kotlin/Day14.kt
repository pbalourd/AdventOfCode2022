import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = File("src/main/kotlin/input14.txt").readLines()

    val cave = List(164) { MutableList(1000) { '.' } }
    var maxY = 0
    for (i in input.indices) {
        input[i].split(" -> ")
            .map { it.split(",") }
            .zipWithNext { a, b ->
                val xa = a[0].toInt()
                val ya = a[1].toInt()
                val xb = b[0].toInt()
                val yb = b[1].toInt()
                maxY = max(maxY, ya)
                maxY = max(maxY, yb)
                if (xa == xb) for (j in min(ya, yb)..max(ya, yb)) cave[j][xa] = '#'
                else for (j in min(xa, xb)..max(xa, xb)) cave[ya][j] = '#'
            }
    }
    for (i in 0 until 1000) cave[maxY + 2][i] = '#'

    maxY++
    val sandX = 500
    val sandY = -1
    var fallen = false
    var countBeforeAbyss = 0
    var countBeforeBlocked = 0
    while(cave[0][sandX] == '.') {
        countBeforeBlocked++
        var x = sandX
        var y = sandY
        while (true) {
            if (cave[y + 1][x] == '.') {
                y++
            } else if (cave[y + 1][x - 1] == '.') {
                y++
                x--
            } else if (cave[y + 1][x + 1] == '.') {
                y++
                x++
            } else {
                cave[y][x] = 'O'
                break
            }

            if (y == maxY) fallen = true
        }
        if (!fallen) countBeforeAbyss++
//        printCave(cave, 485, 515, 0, 11)
    }
    println(countBeforeAbyss)
    // Solution 674

    println(countBeforeBlocked)
    // Solution 24958
}

fun printCave(cave: List<MutableList<Char>>, xa: Int, xb:Int, ya: Int, yb: Int) {
    for (y in ya..yb) {
        for (x in xa..xb) {
            print(cave[y][x])
        }
        println()
    }
}

