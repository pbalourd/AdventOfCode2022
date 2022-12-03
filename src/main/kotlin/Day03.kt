import java.io.File

fun main() {
    val input = File("src/main/kotlin/input03.txt")

    val lines = input.readLines()
    val compartments = lines
        .map { line ->
        val compartmentLength = line.length / 2
        val first = line.substring(0, compartmentLength)
        val second = line.substring(compartmentLength, line.length)
        Pair(first, second)
    }

    var countPart1 = 0
    compartments.forEach {
        for (c in it.first) {
            if (c in it.second) {
                countPart1 += itemPriority(c)
                break
            }
        }
    }

    println(countPart1)
    // Solution 7878

    var countPart2 = 0

    var index = 0
    while (index < lines.size) {
        for (c in lines[index]) {
            if ((c in lines[index + 1]) && (c in lines[index + 2])) {
                countPart2 += itemPriority(c)
                break
            }
        }
        index += 3
    }

    println(countPart2)
    //Solution 2760
}

fun itemPriority(c: Char) =
    when (c) {
        in 'a'..'z' -> c.code - 96
        in 'A'..'Z' -> c.code - 38
        else -> 0
    }