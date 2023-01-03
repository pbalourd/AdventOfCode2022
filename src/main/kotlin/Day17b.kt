import java.io.File
import kotlin.math.max

const val ROCK_TYPES = 5

fun main() {
    val pattern = File("src/main/kotlin/input17.txt").readText()
    val patternSize = pattern.length
    // 10091 (2^14 = 16,384)

    val part1NumOfRocks = 20220
    val part2NumOfRocks = 1_000_000_000_000L

    val ch = CircularBooleanArray()
    ch[0].forEachIndexed { index, _ -> ch[0, index] = true }

    // pattern position 14 bits + 3 bits rock type + 10 rows of 3 bits each
    fun getState(ch: CircularBooleanArray, height: Long, patternPosition: Int, nextRockType: Int): Long {
        var state = patternPosition.toLong()
        state = state or (nextRockType.toLong() * 16384)
        var t = 131072
        for (i in 0L..9) {
            var rowNum = 0
            var addPowTwo = 1
            for (j in 0..6) {
                rowNum += if (ch[height - i][j]) addPowTwo else 0
                addPowTwo *= 2
            }
            t *= 8
        }
        return state
    }

    val cache = mutableMapOf<Long, Pair<Long, Long>>()

    var rockType = 0
    var patternPosition = 0
    var numOfRocks = 1L
    var height = 0L
    while(numOfRocks <= part2NumOfRocks) {
        if (numOfRocks > 1000) {
            val state = getState(ch, height, patternPosition, rockType)
            val previouslyFound = cache[state]
            if (previouslyFound != null) {
                val numOfRocksDiff = numOfRocks - previouslyFound.second - 1
                if (numOfRocks + numOfRocksDiff < part2NumOfRocks) {
//                    println("$numOfRocks ${height} ${cache[state]} ${height - cache[state]!!.first}")
                    numOfRocks += numOfRocksDiff
                    height += height - previouslyFound.first
                }
            }
           cache[state] = Pair(height, numOfRocks - 1)
        }

        var y = ch.pointer + 4
        var x = 2
        var rested = false
        while (!rested) {
            if ( pattern[patternPosition] == '>' && x + rocks[rockType].width <= 6 && rocks[rockType].canMoveRight(ch, y, x) ) {
                x++
            } else if ( pattern[patternPosition] == '<' && x > 0 && rocks[rockType].canMoveLeft(ch, y, x) ) {
                x--
            }
            if (rocks[rockType].canMoveDown(ch, y, x)) y--
            else rested = true
            patternPosition = (patternPosition + 1) % patternSize
        }
        for (dot in rocks[rockType].dots) ch[y + dot.first, x + dot.second] = true
        val newPointer = max(ch.pointer, y + rocks[rockType].height - 1)
        height += newPointer - ch.pointer
        ch.pointer = newPointer
        rockType = (rockType + 1) % ROCK_TYPES

        numOfRocks++
    }
    println(height)
    // Solution 1523167155404
}

class Rock (val dots: List<Pair<Int, Int>>, val width: Int, val height: Int,
            val canMoveDown: (CircularBooleanArray, Long, Int) -> Boolean,
            val canMoveRight: (CircularBooleanArray, Long, Int) -> Boolean,
            val canMoveLeft: (CircularBooleanArray, Long, Int) -> Boolean
)

val rocks = listOf(
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)), 4, 1,
        { chamber, y, x -> !chamber[y - 1, x] && !chamber[y - 1, x + 1] && !chamber[y - 1, x + 2] && !chamber[y - 1, x + 3] },
        { chamber, y, x -> !chamber[y, x + 4] },
        { chamber, y, x -> !chamber[y, x - 1] }),
    Rock(listOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, 2), Pair(2, 1)), 3, 3,
        { chamber, y, x -> !chamber[y, x] && !chamber[y - 1, x + 1] && !chamber[y, x + 2] },
        { chamber, y, x -> !chamber[y, x + 2] && !chamber[y + 1, x + 3] && !chamber[y + 2, x + 2] },
        { chamber, y, x -> !chamber[y, x] && !chamber[y + 1, x - 1] && !chamber[y + 2, x] }),
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 2), Pair(2, 2)), 3, 3,
        { chamber, y, x -> !chamber[y - 1, x] && !chamber[y - 1, x + 1] && !chamber[y - 1, x + 2] },
        { chamber, y, x -> !chamber[y, x + 3] && !chamber[y + 1, x + 3] && !chamber[y + 2, x + 3] },
        { chamber, y, x -> !chamber[y, x - 1] && !chamber[y + 1, x + 1] && !chamber[y + 2, x + 1] }),
    Rock(listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)), 1, 4,
        { chamber, y, x -> !chamber[y - 1, x] },
        { chamber, y, x -> !chamber[y, x + 1] && !chamber[y + 1, x + 1] && !chamber[y + 2, x + 1] && !chamber[y + 3, x + 1] },
        { chamber, y, x -> !chamber[y, x - 1] && !chamber[y + 1, x - 1] && !chamber[y + 2, x - 1] && !chamber[y + 3, x - 1] }),
    Rock(listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(1, 1)), 2, 2,
        { chamber, y, x -> !chamber[y - 1, x] && !chamber[y - 1, x + 1] },
        { chamber, y, x -> !chamber[y, x + 2] && !chamber[y + 1, x + 2] },
        { chamber, y, x -> !chamber[y, x - 1] && !chamber[y + 1, x - 1] })
)

class CircularBooleanArray {
    private val mSize = 20_000
    private val matrix = Array(mSize) { BooleanArray(7) }
    var pointer = 0L
    private var cleanPointer = (mSize - 1).toLong()

    operator fun get(row: Long, x: Int): Boolean {
        return matrix[( row % mSize ).toInt()][x]
    }
    operator fun get(row: Long): BooleanArray {
        return matrix[( row % mSize ).toInt()]
    }

    operator fun set(row: Long, x: Int, v: Boolean) {
        if ( cleanPointer - pointer < mSize / 4 ) {
            for (i in cleanPointer..cleanPointer + mSize / 4)
                matrix[(i % mSize).toInt()].fill(false)
            cleanPointer += mSize / 4
        }
        matrix[( row % mSize ).toInt()][x] = v
    }

}

