import java.io.File

fun main() {
    val input = File("src/main/kotlin/input06.txt")
        .readText()

    println(findMarker(input, 4))
    // Solution 1480

    println(findMarker(input, 14))
    //Solution 2746

}

fun findMarker(buffer: String, size: Int): Int? {
    for (i in 0 until buffer.length-size) {
        val s = buffer.substring(i,i + size).chunked(1).distinct()
            .size
        if (s == size) return i + size
    }
    return null
}

