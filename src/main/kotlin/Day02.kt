import java.io.File

fun main() {
    val pairs = File("src/main/kotlin/input02.txt").readLines()
        .map{ it.split(" ") }
        .map { it -> listOf(it[0][0].code - 'A'.code, it[1][0].code - 'X'.code) }

    var totalScore = 0
    for (pair in pairs) totalScore += getScorePart1(pair[0], pair[1])
    println(totalScore)
    // Solution 10994

    totalScore = 0
    for (pair in pairs) totalScore += getScorePart2(pair[0], pair[1])
    println(totalScore)
    //Solution 12526
}

fun getScorePart1(a: Int, b: Int): Int {
    return (b + 1) + if (a == b) 3 else if ((a + 1) % 3 == b) 6 else 0
}

fun getScorePart2(a: Int, b: Int): Int {
    return b * 3 + 1 + if (b == 1) a else if (b == 2) (a + 1) % 3 else (a + 2) % 3
}

