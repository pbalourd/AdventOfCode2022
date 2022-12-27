import java.io.File
import kotlin.math.abs

data class Positions(val sensorX: Int, val sensorY: Int, val beaconX: Int, val beaconY: Int, val distance: Int)

fun main() {
    val row = 2000000
    val max = 4000000

    val listPositions = File("src/main/kotlin/input15.txt").readLines()
        .map { it.split("Sensor at x=", ", y=", ": closest beacon is at x=").drop(1) }
        .map { Positions( it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt(),
            abs(it[2].toInt() - it[0].toInt()) + abs(it[3].toInt() - it[1].toInt()) )
        }

    val occupiedXRanges = getRanges(listPositions, row)
    val countXPos = countRowPositions(occupiedXRanges)

    val countSensorsBeaconsOnRow = mutableSetOf<Int>()
    for (pos in listPositions) {
        if (pos.beaconY == row) countSensorsBeaconsOnRow.add(pos.beaconY)
        if (pos.sensorY == row) countSensorsBeaconsOnRow.add(pos.sensorY)
    }

    println(countXPos - countSensorsBeaconsOnRow.size)
    // Solution 5240818

    val listOfRowRanges = (0..max)
        .map { y -> getRanges(listPositions, y)
            .sortedBy { it.first }
        }

    for (y in 0..max) {
        var totalRange: IntRange = listOfRowRanges[y][0]
        for (i in 1..listOfRowRanges[y].lastIndex) {
            val f1 = totalRange.first
            val l1 = totalRange.last
            val f2 = listOfRowRanges[y][i].first
            val l2 = listOfRowRanges[y][i].last
            if (l1 + 1 < f2) {
                println(4000000L * (totalRange.last + 1) + y)
                break
            }
            if (l2 > l1) totalRange = f1..l2
        }
//        totalRange = max(totalRange.first, 0)..min(totalRange.last, max)
    }
    // Solution 13213086906101
}

fun getRanges(listPositions: List<Positions>, row: Int): List<IntRange> {
    return listPositions.map {
        val diff = it.distance - abs(it.sensorY - row)
        ((it.sensorX - diff)..(it.sensorX + diff))
    }
        .filter { !it.isEmpty() }
}

fun countRowPositions(occupiedXRanges: List<IntRange>): Int {
    val minXPos = occupiedXRanges.minOf { it.first }
    val maxXPos = occupiedXRanges.maxOf { it.last }

    var countXPos = 0
    for (i in minXPos..maxXPos) {
        for (range in occupiedXRanges) {
            if (i in range) {
                countXPos++
                break
            }
        }
    }
    return countXPos
}

