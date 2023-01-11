import java.io.File

fun main() {
    repeat(2) { turn ->
        val numList = File("src/main/kotlin/input20.txt").readLines()
            .mapIndexed { index, line -> index to if (turn == 0) line.toLong() else 811589153L * line.toLong() }
            .toMutableList()

        val listSize = numList.size

        repeat(if (turn == 0) 1 else 10) {
            for (i in 0 until listSize) {
                val index = numList.indexOf(numList.find { it.first == i })
                val element = numList.removeAt(index)
                val value = element.second

                val newIndex = (index + value).mod(listSize - 1)
                numList.add(newIndex, element)
            }
        }
        val indexZero = numList.indexOf(numList.find { it.second == 0L })
        println(numList[(indexZero + 1000).mod(listSize)].second +
                numList[(indexZero + 2000).mod(listSize)].second +
                numList[(indexZero + 3000).mod(listSize)].second)
    }
    // Solution 11123
    // Solution 4248669215955
}

