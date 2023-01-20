import java.io.File

fun main() {
    val snafuNumbers = File("src/main/kotlin/input25.txt").readLines()

    println( longToSnafu( snafuNumbers.sumOf { snafuToLong(it) } ) )
    // Solution 2-0-01==0-1=2212=100
}

fun snafuToLong(snafu: String): Long {
    val snafuDigitsMap = mapOf('2' to 2L, '1' to 1, '0' to 0, '-' to -1, '=' to -2)
    return snafu.fold(0L) { acc, ch -> acc * 5L + snafuDigitsMap[ch]!! }
}

fun longToSnafu(num: Long): String {
    val decimalDigitsList = listOf('0', '1', '2', '=', '-')
    var n = num
    return buildString {
        while (n > 0) {
            insert(0, decimalDigitsList[(n % 5).toInt()])
            n = (n + 2) / 5
        }
    }
}

