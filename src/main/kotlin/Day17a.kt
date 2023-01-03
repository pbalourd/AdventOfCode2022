package day17a

import java.io.File
import kotlin.math.max

const val ROCK_TYPES = 5

fun main() {
    val pattern = File("src/main/kotlin/input17.txt").readText()
    val patternSize = pattern.length
    // 10091

    val part1NumOfRocks = 2022

    val chamber = Array(part1NumOfRocks * 4) { BooleanArray(7) }
    chamber[0].forEachIndexed { index, _ -> chamber[0][index] = true }

    fun printChamber(startRow: Int, endRow: Int) {
        for (i in endRow + 2 downTo startRow) {
            print('|')
            for (j in 0 until 7)
                if (chamber[i][j]) print('#') else print('.')
            println('|')
        }
        println("+-------+")
    }

    var height = 0
    var rockType = 0
    var patternPosition = 0
    repeat(part1NumOfRocks) {
        var y = height + 4
        var x = 2
        var rested = false
        while (!rested) {
            if (pattern[patternPosition] == '>' && x + rocks[rockType].width <= 6 && rocks[rockType].canMoveRight(
                    chamber,
                    y,
                    x
                )
            ) {
                x++
            } else if (pattern[patternPosition] == '<' && x > 0 && rocks[rockType].canMoveLeft(chamber, y, x)) {
                x--
            }
            if (rocks[rockType].canMoveDown(chamber, y, x)) y--
            else rested = true
            patternPosition = (patternPosition + 1) % patternSize
        }
        for (dot in rocks[rockType].dots) chamber[y + dot.first][x + dot.second] = true
        height = max(height, y + rocks[rockType].height - 1)
        rockType = (rockType + 1) % ROCK_TYPES
    }
    //    printChamber(height - 20, height)
    println(height)
    // Solution 3069

}

class Rock (val dots: List<Pair<Int, Int>>, val width: Int, val height: Int,
            val canMoveDown: (Array<BooleanArray>, Int, Int) -> Boolean,
            val canMoveRight: (Array<BooleanArray>, Int, Int) -> Boolean,
            val canMoveLeft: (Array<BooleanArray>, Int, Int) -> Boolean
)

val rocks = listOf(
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)), 4, 1,
        { chamber, y, x -> !chamber[y - 1][x] && !chamber[y - 1][x + 1] && !chamber[y - 1][x + 2] && !chamber[y - 1][x + 3] },
        { chamber, y, x -> !chamber[y][x + 4] },
        { chamber, y, x -> !chamber[y][x - 1] }),
    Rock(listOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, 2), Pair(2, 1)), 3, 3,
        { chamber, y, x -> !chamber[y][x] && !chamber[y - 1][x + 1] && !chamber[y][x + 2] },
        { chamber, y, x -> !chamber[y][x + 2] && !chamber[y + 1][x + 3] && !chamber[y + 2][x + 2] },
        { chamber, y, x -> !chamber[y][x] && !chamber[y + 1][x - 1] && !chamber[y + 2][x] }),
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 2), Pair(2, 2)), 3, 3,
        { chamber, y, x -> !chamber[y - 1][x] && !chamber[y - 1][x + 1] && !chamber[y - 1][x + 2] },
        { chamber, y, x -> !chamber[y][x + 3] && !chamber[y + 1][x + 3] && !chamber[y + 2][x + 3] },
        { chamber, y, x -> !chamber[y][x - 1] && !chamber[y + 1][x + 1] && !chamber[y + 2][x + 1] }),
    Rock(listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)), 1, 4,
        { chamber, y, x -> !chamber[y - 1][x] },
        { chamber, y, x -> !chamber[y][x + 1] && !chamber[y + 1][x + 1] && !chamber[y + 2][x + 1] && !chamber[y + 3][x + 1] },
        { chamber, y, x -> !chamber[y][x - 1] && !chamber[y + 1][x - 1] && !chamber[y + 2][x - 1] && !chamber[y + 3][x - 1] }),
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(1, 1)), 2, 2,
        { chamber, y, x -> !chamber[y - 1][x] && !chamber[y - 1][x + 1] },
        { chamber, y, x -> !chamber[y][x + 2] && !chamber[y + 1][x + 2] },
        { chamber, y, x -> !chamber[y][x - 1] && !chamber[y + 1][x - 1] })
)

