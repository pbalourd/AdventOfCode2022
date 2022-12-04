import java.io.File

fun main() {
    val input = File("src/main/kotlin/input04.txt")
        .readLines()
        .map { line-> line.split(",", "-")
            .map { it.toInt() }
        }

    val countPart1 = input.count { ranges ->
        (ranges[0]..ranges[1]).subtract(ranges[2]..ranges[3]).isEmpty() ||
                (ranges[2]..ranges[3]).subtract(ranges[0]..ranges[1]).isEmpty()
    }

    println(countPart1)
    // Solution 602

    val countPart2 = input.count { ranges ->
        (ranges[0]..ranges[1])
            .intersect(ranges[2]..ranges[3])
            .isNotEmpty()
    }

    println(countPart2)
    // Solution 891

}

