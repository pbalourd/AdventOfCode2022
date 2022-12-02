import java.io.File

fun main() {
    val input = File("src/main/kotlin/input02.txt")
    val pairs = input.readLines()
        .map{ it.split(" ") }

    var totalScore = 0
    for (pair in pairs) totalScore += getScorePart1(pair[0], pair[1])

    println(totalScore)
    // Solution 10994

    totalScore = 0
    for (pair in pairs) totalScore += getScorePart2(pair[0], pair[1])
    println(totalScore)
}

fun getScorePart1(a: String, b: String): Int {
    var score = when (b) {
        "X" -> 1
        "Y" -> 2
        else -> 3
    }
    if ( (b == "X" && a == "A") ||
        (b == "Y" && a == "B") ||
        (b == "Z" && a == "C") ) score += 3
    else {
        if ( (b == "X" && a == "C") ||
            (b == "Y" && a == "A") ||
            (b == "Z" && a == "B") ) score += 6
    }
    return score
}

fun getScorePart2(a: String, b: String): Int {
    var score = when (b) {
        "X" -> 0
        "Y" -> 3
        else -> 6
    }
    if ( (b == "X" && a == "B") ||
        (b == "Y" && a == "A") ||
        (b == "Z" && a == "C") ) score += 1
    if ( (b == "X" && a == "C") ||
        (b == "Y" && a == "B") ||
        (b == "Z" && a == "A") ) score += 2
    if ( (b == "X" && a == "A") ||
        (b == "Y" && a == "C") ||
        (b == "Z" && a == "B") ) score += 3
    return score
}