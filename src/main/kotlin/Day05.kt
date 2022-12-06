import java.io.File

fun main() {
    val input = File("src/main/kotlin/input05.txt")

    val a = input.readLines()
    val b = input.readText()

    val c = a.takeWhile { it != "" }

    val p = c.last().split(" ").last().toInt()
    val r = c.dropLast(1)
    val d = a.takeLastWhile { it != "" }.map { line ->
        line.split("move ", " from ", " to ")
//            .map { it.drop(1) }
    }
        .map { it.drop(1) }
        .map { it.map { a -> a.toInt() } }


    val h = mutableListOf<MutableList<String>>()
    var t = 1
    for (d in 1..p) {

        val f = mutableListOf<String>()
        for (w in r) {

            if (t >= w.length) continue
            val hh = w.substring(t, t + 1)
            if (hh != " ") f.add(hh)
        }
        t += 4
        h.add(f.reversed().toMutableList())
//        println(h)
    }

//    for (ss in d) {
//        repeat(ss[0]) {
//            val ff = h[ss[1] - 1].removeLast()
//            h[ss[2] - 1].add(ff)
//        }
//    }
    for (ss in d) {
        val bb = mutableListOf<String>()
        repeat(ss[0]) {
            val ff = h[ss[1] - 1].removeLast()
            bb.add(ff)
        }
        repeat(ss[0]) {
            h[ss[2] - 1].add(bb[ss[0] - it - 1])
        }

    }

    for (jjj in 1..p) {
        println(h[jjj - 1].last())
    }

}