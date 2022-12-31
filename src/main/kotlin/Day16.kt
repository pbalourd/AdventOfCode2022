import java.io.File
import kotlin.math.max

data class Quadruple<A,B,C,D>(var first: A, var second: B, var third: C, var fourth: D)

fun main() {
    val data = File("src/main/kotlin/input16.txt").readLines()
        .map { it.substringAfter("Valve ") }
        .map { it.split(" has flow rate=", "; tunnels lead to valves ", "; tunnel leads to valve ") }.associate {
            it[0] to Pair(it[1].toInt(), it[2].split(", "))
        }.toMutableMap()

    val valves = data.keys.toList()
    val cache = mutableMapOf<Triple<String, Int, Long>, Int>()

    fun solve1(presentValve: String, time: Int, openValves: Long): Int {
        if (time == 0) return 0
        val cacheValue = cache[Triple(presentValve, time, openValves)]
        if (cacheValue != null) return cacheValue

        var maxValue = 0
        val currValveIndex = valves.indexOf(presentValve)
        val positionInLong = 1L shl currValveIndex
        val openValveLong = positionInLong and openValves

        var openValvesSum = 0
        for (i in 0..valves.lastIndex) {
            if (openValves and (1L shl i) != 0L) {
                openValvesSum += data[valves[i]]!!.first
            }
        }

        if (openValveLong == 0L && data[presentValve]!!.first > 0) {
            val newOpenValves = openValves or positionInLong
            maxValue = openValvesSum + solve1(presentValve, time - 1, newOpenValves)
        }
        for (valve in data[presentValve]!!.second) {
            maxValue = max(maxValue, openValvesSum + solve1(valve, time - 1, openValves))
        }

        cache[Triple(presentValve, time, openValves)] = maxValue
        return maxValue
    }

    println(solve1("AA", 30, 0L))
    // Solution 1580

    val cache2 = mutableMapOf<Quadruple<String, Int, Long, Int>, Int>()

    fun solve2(presentValve: String, time: Int, openValves: Long, workers: Int): Int {
        if (time == 0) return if (workers > 1) solve2("AA", 26, openValves, workers - 1) else 0

        val cacheValue = cache2[Quadruple(presentValve, time, openValves, workers)]
        if (cacheValue != null) return cacheValue

        var maxValue = 0
        val currValveIndex = valves.indexOf(presentValve)
        val positionInLong = 1L shl currValveIndex
        val openValveLong = positionInLong and openValves

        if (openValveLong == 0L && data[presentValve]!!.first > 0) {
            val newOpenValves = openValves or positionInLong
            maxValue = data[presentValve]!!.first * (time - 1) + solve2(presentValve, time - 1, newOpenValves, workers)
        }
        for (valve in data[presentValve]!!.second) {
            maxValue = max(maxValue, solve2(valve, time - 1, openValves, workers))
        }

        cache2[Quadruple(presentValve, time, openValves, workers)] = maxValue
        return maxValue
    }

    println(solve2("AA", 26, 0L, 2))
    // Solution 2213
}

