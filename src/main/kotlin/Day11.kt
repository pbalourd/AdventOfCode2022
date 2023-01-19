import java.io.File

class Monkey {
    lateinit var items: MutableList<Long>
    lateinit var op: (Long) -> Long
    var div = 0L
    var mTrue = 0
    var mFalse = 0
    var count = 0L
}

fun main() {
    val input = File("src/main/kotlin/input11.txt")
        .readText()
        .split("\r\n\r\n")
        .map { it.lines() }

    var monkeys = initiate()
    inspecting(20, monkeys, true)
    // Solution 88208

    monkeys = initiate()
    inspecting(10000, monkeys, false)
    // Solution 21115867968
}

private fun inspecting(rounds: Int, monkeys: MutableList<Monkey>, divide: Boolean) {
    val commonProduct = monkeys.fold(1L) { value, monkey -> value * monkey.div }

    repeat(rounds) {
        for (monkey in monkeys) {
            monkey.count += monkey.items.size
            while (monkey.items.isNotEmpty()) {
                var value = monkey.op(monkey.items.removeFirst())
                value = if (divide) value / 3
                else value % commonProduct
                if (value % monkey.div == 0L) {
                    monkeys[monkey.mTrue].items.add(value)
                } else {
                    monkeys[monkey.mFalse].items.add(value)
                }
            }
        }
    }

    val counts = monkeys.map { it.count }.sortedDescending()
    println(counts[0] * counts[1])
}

fun initiate(): MutableList<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    repeat(8) { monkeys.add(Monkey()) }

    monkeys[0].apply { items = mutableListOf(71L, 86L); op = { a: Long -> a * 13}; div = 19; mTrue = 6; mFalse = 7}
    monkeys[1].apply { items = mutableListOf(66L, 50L, 90L, 53L, 88L, 85L); op = { a: Long -> a + 3}; div = 2; mTrue = 5; mFalse = 4}
    monkeys[2].apply { items = mutableListOf(97L, 54L, 89L, 62L, 84L, 80L, 63L); op = { a: Long -> a + 6 }; div = 13; mTrue = 4; mFalse = 1}
    monkeys[3].apply { items = mutableListOf(82L, 97L, 56L, 92L); op = { a: Long -> a + 2 }; div = 5; mTrue = 6; mFalse = 0}
    monkeys[4].apply { items = mutableListOf(50L, 99L, 67L, 61L, 86L); op = { a: Long -> a * a }; div = 7; mTrue = 5; mFalse = 3}
    monkeys[5].apply { items = mutableListOf(61L, 66L, 72L, 55L, 64L, 53L, 72L, 63L); op = { a: Long -> a + 4 }; div = 11; mTrue = 3; mFalse = 0}
    monkeys[6].apply { items = mutableListOf(59L, 79L, 63L); op = { a: Long -> a * 7 }; div = 17; mTrue = 2; mFalse = 7}
    monkeys[7].apply { items = mutableListOf(55L); op = { a: Long -> a + 7 }; div = 3; mTrue = 2; mFalse = 1}

    return monkeys
}

