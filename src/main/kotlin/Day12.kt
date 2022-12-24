import java.io.File

typealias Point = Pair<Int, Int>

fun main() {
    val grid = File("src/main/kotlin/input12.txt").readLines()
        .map { it.toMutableList() }

    lateinit var start: Point
    lateinit var end: Point
    grid.forEachIndexed { y, chars -> chars.forEachIndexed { x, c ->
            if (c == 'S')  {
                start = Point(y, x)
                grid[y][x] = 'a'
            } else if (c == 'E') {
                end = Point(y, x)
                grid[y][x] = 'z'
            }
        }
    }

    println(findEndPointLevel(grid, start, end))
    // Solution 497

    var countMin = Int.MAX_VALUE
    for (i in grid.indices) {
        for (j in 0 until grid[0].size) {
            if (grid[i][j] == 'a') {
                start = Point(i, j)
                val count = findEndPointLevel(grid, start, end)
                if (count < countMin) countMin = count
            }
        }
    }
    println(countMin)
    // Solution 492
}

private fun findEndPointLevel(grid: List<MutableList<Char>>, start: Point, end: Point): Int {
    val ySize = grid.size
    val xSize = grid[0].size

    var currentLevel = ArrayDeque<Point>()
    val visited = List(ySize) { MutableList(xSize) { false } }

    currentLevel.add(start)
    var count = 0
    while (currentLevel.isNotEmpty()) {
        val nextLevel = ArrayDeque<Point>()
        while (currentLevel.isNotEmpty()) {
            val (y, x) = currentLevel.removeFirst()
            if (visited[y][x]) continue
            else visited[y][x] = true
            if (Point(y, x) == end) return count
            val value = grid[y][x]
            if (y >= 1 && grid[y - 1][x] - value <= 1) nextLevel.addLast(Point(y - 1, x))
            if (y < ySize - 1 && grid[y + 1][x] - value <= 1) nextLevel.addLast(Point(y + 1, x))
            if (x >= 1 && grid[y][x - 1] - value <= 1) nextLevel.addLast(Point(y, x - 1))
            if (x < xSize - 1 && grid[y][x + 1] - value <= 1) nextLevel.addLast(Point(y, x + 1))
        }
        currentLevel = nextLevel
        count++
    }
    return Int.MAX_VALUE
}

