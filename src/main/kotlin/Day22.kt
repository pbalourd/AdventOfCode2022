import java.io.File

fun main() {
    val input = File("src/main/kotlin/input22.txt").readLines()
    val map = mutableMapOf<Pair<Int, Int>, Boolean>()
    input.takeWhile { it != "" }
        .forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c == '.') map[Pair(i,j)] = true
                else if (c == '#') map[Pair(i,j)] = false
            }
        }

    val path = """[LR]|\d+""".toRegex()
        .findAll(input.takeLastWhile { it != "" }.first())
        .map { it.value }

    val minRow = 0
    val minColumn = map.filter { it.key.first == 0 }.minOf { it.key.second }

    var facing = 0
    var position = Pair(minRow, minColumn)
    for (move in path) {
        if (move == "R") facing = (facing + 1) % 4
        else if (move == "L") facing = (facing + 3) % 4
        else {
            val steps = move.toInt()
            for (n in 1..steps) {
                val checkPos = when(facing) {
                    0 -> {
                        if (map[Pair(position.first, position.second + 1)] == null) {
                            Pair(position.first, map.filter { it.key.first == position.first }.minOf { it.key.second })
                        } else Pair(position.first, position.second + 1)
                    }
                    1 -> {
                        if (map[Pair(position.first + 1, position.second)] == null) {
                            Pair(map.filter { it.key.second == position.second }.minOf { it.key.first }, position.second)
                        } else Pair(position.first + 1, position.second)
                    }
                    2 -> {
                        if (map[Pair(position.first, position.second - 1)] == null) {
                            Pair(position.first, map.filter { it.key.first == position.first }.maxOf { it.key.second })
                        } else Pair(position.first, position.second - 1)
                    }
                    else -> {
                        if (map[Pair(position.first - 1, position.second)] == null) {
                            Pair(map.filter { it.key.second == position.second }.maxOf { it.key.first }, position.second)
                        } else Pair(position.first - 1, position.second)
                    }
                }
                if (!map[checkPos]!!) break
                else position = checkPos
            }
        }
    }
    println((position.first + 1) * 1000 + (position.second + 1) * 4 + facing)
    // Solution 73346

    facing = 0
    position = Pair(minRow, minColumn)
    for (move in path) {
        if (move == "R") facing = (facing + 1) % 4
        else if (move == "L") facing = (facing + 3) % 4
        else {
            val steps = move.toInt()
            for (n in 1..steps) {
                var checkPosition = Pair(minRow, minColumn)
                var checkFacing = 0
                when(facing) {
                    0 -> {
                        if (position.first in 0..49 && position.second == 149) {
                            checkPosition = Pair(149 - position.first, 99)
                            checkFacing = 2
                        } else if (position.first in 50..99 && position.second == 99) {
                            checkPosition = Pair(49, position.first + 50)
                            checkFacing = 3
                        } else if (position.first in 100..149 && position.second == 99) {
                            checkPosition = Pair(149 - position.first, 149)
                            checkFacing = 2
                        } else if (position.first in 150..199 && position.second == 49) {
                            checkPosition = Pair(149, position.first - 100)
                            checkFacing = 3
                        } else {
                            checkPosition = Pair(position.first, position.second + 1)
                            checkFacing = 0
                        }
                    }
                    1 -> {
                        if (position.first == 49 && position.second in 100..149) {
                            checkPosition = Pair(position.second - 50, 99)
                            checkFacing = 2
                        } else if (position.first == 149 && position.second in 50..99) {
                            checkPosition = Pair(position.second + 100, 49)
                            checkFacing = 2
                        } else if (position.first == 199 && position.second in 0..49) {
                            checkPosition = Pair(0, position.second + 100)
                            checkFacing = 1
                        } else {
                            checkPosition = Pair(position.first + 1, position.second)
                            checkFacing = 1
                        }
                    }
                    2 -> {
                        if (position.first in 0..49 && position.second == 50) {
                            checkPosition = Pair(149 - position.first, 0)
                            checkFacing = 0
                        } else if (position.first in 50..99 && position.second == 50) {
                            checkPosition = Pair(100, position.first - 50)
                            checkFacing = 1
                        } else if (position.first in 100..149 && position.second == 0) {
                            checkPosition = Pair(149 - position.first, 50)
                            checkFacing = 0
                        } else if (position.first in 150..199 && position.second == 0) {
                            checkPosition = Pair(0, position.first - 100)
                            checkFacing = 1
                        } else {
                            checkPosition = Pair(position.first, position.second - 1)
                            checkFacing = 2
                        }
                    }
                    3 -> {
                        if (position.first == 100 && position.second in 0..49) {
                            checkPosition = Pair(position.second + 50, 50)
                            checkFacing = 0
                        } else if (position.first == 0 && position.second in 50..99) {
                            checkPosition = Pair(position.second + 100, 0)
                            checkFacing = 0
                        } else if (position.first == 0 && position.second in 100..149) {
                            checkPosition = Pair(199, position.second - 100)
                            checkFacing = 3
                        } else {
                            checkPosition = Pair(position.first - 1, position.second)
                            checkFacing = 3
                        }
                    }
                }
                if (!map[checkPosition]!!) break
                else {
                    position = checkPosition
                    facing = checkFacing
                }
            }
        }
    }
    println((position.first + 1) * 1000 + (position.second + 1) * 4 + facing)
    // Solution 106392
}

