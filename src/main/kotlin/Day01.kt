import java.io.File

fun main() {
    val input = File("src/main/kotlin/input01.txt")
        .readText()
        .split("\r\n\r\n")

    val calories = input
        .map{
            it.lines().sumOf { line -> line.toInt() }
        }

    println(calories.max())
    // Solution 71780

    println(calories.sorted().takeLast(3).sum())
}

