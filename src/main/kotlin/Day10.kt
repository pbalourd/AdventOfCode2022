import java.io.File

fun main() {
    val program = File("src/main/kotlin/input10.txt")
        .readLines()

    var xreg = 1
    var clock = 0
    var sum = 0
    val crt = Array(240) { false }
    for (line in program) {
        val rr = xreg-1..xreg+1
        when (line.substring(0, 4)) {
            "noop" -> {
                clock++
                if (((clock - 1) % 40) in rr) crt[clock - 1] = true
                if (clock == 20 || (clock - 20) % 40 == 0) {
                    sum += xreg * clock
                }
            }
            "addx" -> {
                repeat(2) {
                    clock++
                    if (((clock - 1) % 40) in rr) crt[clock - 1] = true
                    if (clock == 20 || (clock - 20) % 40 == 0) sum += xreg * clock
                }
                val value = line.split(" ")[1].toInt()
                xreg += value
            }
        }
    }
    println(sum)
    for (i in crt.indices) {
        if (crt[i]) print("█") else print(" ")
        if ((i+1) % 40 == 0) println()
    }
    //Solution 13820
    //Solution
//    ████ █  █  ██  ███  █  █  ██  ███  █  █
//       █ █ █  █  █ █  █ █ █  █  █ █  █ █ █
//      █  ██   █    █  █ ██   █    █  █ ██
//     █   █ █  █ ██ ███  █ █  █ ██ ███  █ █
//    █    █ █  █  █ █ █  █ █  █  █ █ █  █ █
//    ████ █  █  ███ █  █ █  █  ███ █  █ █  █

}

