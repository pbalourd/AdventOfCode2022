import java.io.File
import kotlin.math.max

fun main() {
    val input = File("src/main/kotlin/input08.txt")

    val grid = input.readLines()
        .map { line ->
            line.chunked(1)
            .map { it.toInt() }
        }

    val ySize = grid.size
    val xSize = grid[0].size

    var count = 0
    (0 until ySize).forEach { i->
        (0 until xSize).forEach { j ->
            if ( (0 until j).all { k -> grid[i][j] > grid[i][k] } ||
                (j+1 until xSize).all { k -> grid[i][j] > grid[i][k] } ||
                (0 until i).all { k -> grid[i][j] > grid[k][j] } ||
                (i+1 until ySize).all { k -> grid[i][j] > grid[k][j] }
            ) count++
        }
    }
    println(count)
    // Solution 1829

    var maxScore = 0
    (1 until ySize - 1).forEach { i->
        (1 until xSize - 1).forEach { j ->
            val score = ( (j - 1 downTo  0).takeWhileInclusive { k -> grid[i][j] > grid[i][k] }.count() ) *
                    ( (j + 1 until xSize).takeWhileInclusive { k -> grid[i][j] > grid[i][k] }.count() ) *
                    ( (i - 1 downTo 0).takeWhileInclusive { k -> grid[i][j] > grid[k][j] }.count() ) *
                    ( (i + 1 until ySize).takeWhileInclusive { k -> grid[i][j] > grid[k][j] }.count() )

            maxScore = max(maxScore, score)
        }
    }

    println(maxScore)
    //Solution 291840
}

fun <T> Iterable<T>.takeWhileInclusive(pred: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}

