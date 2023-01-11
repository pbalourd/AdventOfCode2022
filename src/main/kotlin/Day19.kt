import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.system.*
import kotlin.time.measureTime

data class AvailableResources(val oreRobots: Int, val clayRobots: Int, val obsRobots: Int, val geodeRobots: Int,
                                val ore: Int, val clay: Int, val obs: Int, val geodes: Int, val timeLeft: Int)

fun main() {

    val blueprintsData = File("src/main/kotlin/input19.txt").readLines()
        .map { line ->
            line
                .substringAfter("Blueprint ")
                .substringBefore(" obsidian.")
                .split(
                    ": Each ore robot costs ",
                    " ore. Each clay robot costs ",
                    " ore. Each obsidian robot costs ",
                    " ore and ",
                    " clay. Each geode robot costs ",
                    " ore and "
                )
                .map { it.toInt() }
        }

    var numGeodesList = calculateGeodes(blueprintsData.size, blueprintsData, 24)
    val p1 = measureNanoTime {
        println(numGeodesList.reduceIndexed { index, acc, i -> acc + (index + 1) * i })
        // Solution 1395
    }
    println("t1: $p1")

    numGeodesList = calculateGeodes(3, blueprintsData, 32)
    val p2 = measureNanoTime {
        println(numGeodesList.reduce { acc, i -> acc * i })
        // Solution 2700
    }
    println("t2: $p2")
}

private fun calculateGeodes(numOfBlueprints: Int, input: List<List<Int>>, totalTime: Int): MutableList<Int> {
    val numGeodesList = mutableListOf<Int>()
    // Multiplier for resources constrain
    val mult = 1.5

    for (i in 0 until numOfBlueprints) {
        val or = input[i][1]
        val cl = input[i][2]
        val oo = input[i][3]
        val oc = input[i][4]
        val go = input[i][5]
        val gb = input[i][6]

        // Robot factory can create only one robot each minute.
        // No need to create extra robots than the number of those required.
        val maxOreRobots = maxOf(or, cl, oo, go)
        val maxClayRobots = oc
        val maxObsRobots = gb

        val start = AvailableResources(1, 0, 0, 0, 0, 0, 0, 0, totalTime)
        val queue = ArrayDeque<AvailableResources>()
        queue.add(start)
        val cache = mutableSetOf<AvailableResources>()
        var numGeodes = 0
        while (queue.isNotEmpty()) {
            var (oreRobots, clayRobots, obsRobots, geodeRobots, ore, clay, obs, geodes, timeLeft) = queue.removeFirst()
            numGeodes = max(numGeodes, geodes)

            if (timeLeft == 0) continue

            ore = min(ore, (maxOreRobots * mult).toInt())
            clay = min(clay, (maxClayRobots * mult).toInt())
            obs = min(obs, (maxObsRobots * mult).toInt())

            // Check if state in cache
            val newAvRes = AvailableResources(oreRobots, clayRobots, obsRobots, geodeRobots, ore, clay, obs, geodes, timeLeft)
            if (newAvRes in cache) continue
            cache.add(newAvRes)

            // No new robot
            queue.add(
                AvailableResources(
                    oreRobots, clayRobots, obsRobots, geodeRobots,
                    ore + oreRobots, clay + clayRobots, obs + obsRobots, geodes + geodeRobots, timeLeft - 1
                )
            )
            // Create ore robot and restrain ore robots to maximum
            if (oreRobots < maxOreRobots && ore >= or) queue.add(
                AvailableResources(
                    oreRobots + 1, clayRobots, obsRobots, geodeRobots,
                    ore + oreRobots - or, clay + clayRobots, obs + obsRobots, geodes + geodeRobots, timeLeft - 1
                )
            )
            // Create clay robot and restrain clay robots to maximum
            if (clayRobots < maxClayRobots && ore >= cl) queue.add(
                AvailableResources(
                    oreRobots, clayRobots + 1, obsRobots, geodeRobots,
                    ore + oreRobots - cl, clay + clayRobots, obs + obsRobots, geodes + geodeRobots, timeLeft - 1
                )
            )
            // Create obsidian robot and restrain obsidian robots to maximum
            if (obsRobots < maxObsRobots && ore >= oo && clay >= oc) queue.add(
                AvailableResources(
                    oreRobots, clayRobots, obsRobots + 1, geodeRobots,
                    ore + oreRobots - oo, clay + clayRobots - oc, obs + obsRobots, geodes + geodeRobots, timeLeft - 1
                )
            )
            // Create geode robot
            if (ore >= go && obs >= gb) queue.add(
                AvailableResources(
                    oreRobots, clayRobots, obsRobots, geodeRobots + 1,
                    ore + oreRobots - go, clay + clayRobots, obs + obsRobots - gb, geodes + geodeRobots, timeLeft - 1
                )
            )
        }
        numGeodesList.add(numGeodes)
    }
    return numGeodesList
}

