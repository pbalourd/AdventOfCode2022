import java.io.File

data class Blizzard(var row: Int, var col: Int, var direction: Char)

fun main() {
    val input = File("src/main/kotlin/input24.txt").readLines()

    val height = input.size
    val width = input[0].length - 1
    val blizzards = input.asSequence()
        .mapIndexed { row, line ->
            line.mapIndexed { col, ch ->
                if (ch in listOf('>', '<', '^', 'v'))
                    Blizzard(row, col, ch)
                else null
            }
        }
        .flatten()
        .filterNotNull()
        .toMutableSet()

    fun moveBlizzards() {
        for (blizzard in blizzards) {
            when(blizzard.direction) {
                '>' -> blizzard.col = (blizzard.col % (width - 1) ) + 1
                '<' -> blizzard.col = (blizzard.col - 2).mod(width - 1) + 1
                '^' -> blizzard.row =(blizzard.row - 2).mod(height - 2) + 1
                'v' -> blizzard.row = (blizzard.row % (height - 2) ) + 1
//                '>' -> blizzard.col = if (blizzard.col == width - 1) 1 else blizzard.col + 1
//                '<' -> blizzard.col = if (blizzard.col == 1) width - 1 else blizzard.col - 1
//                '^' -> blizzard.row = if (blizzard.row == 1) height - 2 else blizzard.row - 1
//                'v' -> blizzard.row = if (blizzard.row == height - 2) 1 else blizzard.row + 1
            }
        }
    }

    fun getNextPositions(y: Int, x: Int): List<Pair<Int, Int>> {
        val positions = mutableListOf<Pair<Int, Int>>()
        if (y == height - 2 && x == width - 1) positions.add(Pair(height - 1, width - 1))
        if (y == 1 && x == 1) positions.add(Pair(0, 1))
        if (blizzards.find { it.row == y && it.col == x } == null) positions.add(Pair(y, x))
        if (y > 1 && blizzards.find { it.row == y - 1 && it.col == x } == null) positions.add(Pair(y - 1, x))
        if (y < height - 2 && blizzards.find { it.row == y + 1 && it.col == x } == null) positions.add(Pair(y + 1, x))
        if (x > 1 && y !=0 && y!= height - 1 && blizzards.find { it.row == y && it.col == x - 1 } == null) positions.add(Pair(y, x - 1))
        if (x < width - 1 && y !=0 && y!= height - 1 && blizzards.find { it.row == y && it.col == x + 1 } == null) positions.add(Pair(y, x + 1))

        return positions
    }

    val topLeftOpening = Pair(0, 1)
    val bottomRightOpening = Pair(height - 1, width - 1)
    var start = topLeftOpening
    var end = bottomRightOpening

    var minute = 0
    var elvesPositions = mutableSetOf(start)
    var phase = 0
    while (true) {
        minute++
        moveBlizzards()
        val newElvesPositions = mutableSetOf<Pair<Int, Int>>()
        for (elvesPosition in elvesPositions) {
            newElvesPositions.addAll(getNextPositions(elvesPosition.first, elvesPosition.second))
        }
        elvesPositions = newElvesPositions
        if (end in elvesPositions) {
            if (phase == 0) {
                phase++
                start = bottomRightOpening
                end = topLeftOpening
                elvesPositions.clear()
                elvesPositions.add(start)
                println(minute)
                // Solution 238 (Part 1)
            } else if (phase == 1) {
                phase++
                start = topLeftOpening
                end = bottomRightOpening
                elvesPositions.clear()
                elvesPositions.add(start)
            } else {
                println(minute)
                // Solution 751 (Part 2)
                break
            }
        }
    }
}

