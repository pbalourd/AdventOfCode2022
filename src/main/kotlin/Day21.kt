import java.io.File

fun main() {
    val regex = """[a-z]{4}|[+-/*]|\d+""".toRegex()
    val input = File("src/main/kotlin/input21.txt").readLines()
        .map { line -> regex.findAll(line)
            .toList()
            .map { it.value }
        }
        .associate { list -> list[0] to list.drop(1) }.toMutableMap()

    fun getValue(monkeyName: String): Long? {
        val list = input[monkeyName] ?: return null
        return if (list.size == 1) list[0].toLong()
        else {
            val a = getValue(list[0]) ?: return null
            val b = getValue(list[2]) ?: return null
            when(list[1]) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> a / b
                else -> throw Exception("Error")
            }
        }
    }
    println(getValue("root"))
    // Solution 38731621732448

    fun getValueReverse(monkeyName: String, value: Long) {
        if (monkeyName == "humn") {
            println(value)
        } else {
            val list = input[monkeyName]!!
            val a = getValue(list[0])
            if (a == null) {
                val b = getValue(list[2])!!
                when(list[1]) {
                    "+" -> getValueReverse(list[0], value - b)
                    "-" -> getValueReverse(list[0], value + b)
                    "*" -> getValueReverse(list[0], value / b)
                    "/" -> getValueReverse(list[0], value * b)
                }
            } else {
                when(list[1]) {
                    "+" -> getValueReverse(list[2], value - a)
                    "-" -> getValueReverse(list[2], a - value)
                    "*" -> getValueReverse(list[2], value / a)
                    "/" -> getValueReverse(list[2], a / value)
                }
            }
        }
    }

    //    test root: pppw + sjmn
    //    root: lsbv + bsgz
    input.remove("humn")
    getValueReverse("lsbv", getValue("bsgz")!!)
    // Solution 3848301405790
}

