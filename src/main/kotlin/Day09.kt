import java.io.File
import java.lang.Exception
import java.lang.Math.abs

fun main() {
    val input = File("src/main/kotlin/input09.txt")

    val aa = input.readLines()

    val bb = aa.map { line -> line.split(" ") }

    var x = 0
    var y = 0
    var xMax = 0
    var xMin = 0
    var yMax = 0
    var yMin = 0
    bb.forEach {
        when (it[0]) {
            "R" -> x += it[1].toInt()
            "L" -> x -= it[1].toInt()
            "U" -> y += it[1].toInt()
            "D" -> y -= it[1].toInt()
        }
        if (x > xMax) xMax = x
        if (x < xMin) xMin = x
        if (y > yMax) yMax = y
        if (y < yMin) yMin = y
    }

    println("$x $y")
    println("$xMin $xMax")
    println("$yMin $yMax")

    val m = Array(1000) { Array<Boolean>(1000) { false } }

    x = 500
    y = 500
    var xt = x
    var yt = y
    m[xt][yt] = true
    bb.forEach { ee ->
        repeat(ee[1].toInt()) {
            when (ee[0]) {
                "R" -> {
                    x += 1
                }

                "L" -> {
                    x += -1
                }

                "U" -> {
                    y += 1
                }

                "D" -> {
                    y += -1
                }
            }
            val g = tailMove(x, y, xt, yt)
            xt = g.first
            yt = g.second
            m[xt][yt] = true
        }

    }

    var count = 0
    for (i in m.indices) {
        for (j in m[0].indices) {
            if (m[i][j]) count++
        }
    }
    println(count)



    val m2 = Array(1000) { Array<Boolean>(1000) { false } }
    x = 500
    y = 500
    val p = Array<MutableList<Int>>(9) { mutableListOf(x ,y) }
    m2[x][y] = true

    bb.forEach { ee ->
        repeat(ee[1].toInt()) {
            when (ee[0]) {
                "R" -> {
                    x += 1
                }

                "L" -> {
                    x += -1
                }

                "U" -> {
                    y += 1
                }

                "D" -> {
                    y += -1
                }
            }
            for (i in p.indices) {
                val g = if (i == 0) tailMove(x, y, p[i][0], p[i][1])
                else tailMove(p[i - 1][0], p[i - 1][1], p[i][0], p[i][1])

                p[i][0] = g.first
                p[i][1] = g.second

            }
            m2[p[8][0]][p[8][1]] = true

        }

    }
    count = 0
    for (i in m2.indices) {
        for (j in m2[0].indices) {
            if (m2[i][j]) count++
        }
    }
    println(count)

}

fun tailMove(x: Int, y:Int, xt: Int, yt:Int): Pair<Int, Int> {
    return if (kotlin.math.abs(x - xt) <= 1 && kotlin.math.abs(y - yt) <= 1) {
        Pair(xt,yt)
    }
    else if (y == yt) {
        Pair(xt+(x-xt)/2, yt)
    } else if (x == xt) {
        Pair(xt, yt+(y-yt)/2)
    } else if (y-yt>0 && x-xt>0) {
        Pair(xt+1, yt+1)
    } else if (y-yt<0 && x-xt>0) {
        Pair(xt+1, yt-1)
    }
    else if (y-yt>0 && x-xt<0) {
        Pair(xt-1, yt+1)
    } else if (y-yt<0 && x-xt<0) {
        Pair(xt-1, yt-1)
    }
    else {
        throw Exception("False")
    }
}

