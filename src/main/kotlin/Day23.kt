import java.io.File

fun main() {
    val elves = File("src/main/kotlin/input23.txt").readLines()
        .asSequence()
        .mapIndexed { row, line -> line.toList()
            .mapIndexed { col, ch -> Pair(row, col) to ch }
        }
        .flatten()
        .filter { it.second == '#' }
        .map { it.first }
        .toMutableSet()

    val adjFunctions = listOf(
            {row: Int, col: Int ->
                if ( Pair(row - 1, col) !in elves &&
                    Pair(row - 1, col + 1) !in elves &&
                    Pair(row - 1, col - 1) !in elves ) Pair(row - 1, col)
                else null
            },
            {row, col ->
                if (Pair(row + 1, col) !in elves &&
                    Pair(row + 1, col + 1) !in elves &&
                    Pair(row + 1, col - 1) !in elves) Pair(row + 1, col)
                else null
            },
            {row, col ->
                if (Pair(row, col - 1) !in elves &&
                    Pair(row - 1, col - 1) !in elves &&
                    Pair(row + 1, col - 1) !in elves) Pair(row, col - 1)
                else null
            },
            {row, col ->
                if (Pair(row, col + 1) !in elves &&
                    Pair(row - 1, col + 1) !in elves &&
                    Pair(row + 1, col + 1) !in elves) Pair(row, col + 1)
                else null
            }
    )

    fun checkEightPositions(elf: Pair<Int, Int>): Boolean {
        val row = elf.first
        val col = elf.second
        return Pair(row - 1, col) !in elves && Pair(row - 1, col + 1) !in elves &&
                Pair(row, col + 1) !in elves && Pair(row + 1, col + 1) !in elves &&
                Pair(row + 1, col) !in elves && Pair(row + 1, col - 1) !in elves &&
                Pair(row, col - 1) !in elves && Pair(row - 1, col - 1) !in elves
    }

    var round = 0
    var direction = 0
    while (true) {
        round++
        val moves = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (elf in elves) {
            if (checkEightPositions(elf)) continue
            for (n in 0..3) {
                val funNum = (direction + n) % 4
                val move = adjFunctions[funNum](elf.first, elf.second)
                if (move != null) {
                    moves.add(Pair(elf, move))
                    break
                }
            }
        }
        if (moves.isEmpty()) {
            println(round)
            // Solution 1049 (part 2)
            break
        }
        val destinations = moves.map { it.second }
        for (move in moves) {
            if (destinations.count { it == move.second } == 1 ) {
                elves.remove(move.first)
                elves.add(move.second)
            }
        }
        direction = (direction + 1) % 4
        if (round == 10) {
            val minY = elves.minOf { it.first }
            val maxY = elves.maxOf { it.first }
            val minX = elves.minOf { it.second }
            val maxX = elves.maxOf { it.second }

            println((maxY - minY + 1) * (maxX - minX + 1) - elves.size)
            // Solution 4247 (part 1)
        }
    }
}

