import java.io.File

data class SideOfCube(val x: Int, val y: Int, val z:Int, val direction: Int)
// Direction +1 x positive, -1 x negative, +2 y pos, -2 y neg, +3 z pos, -3 z neg

fun main() {
    val cubePositions = File("src/main/kotlin/input18.txt").readLines()
        .map { line ->
            line.split(",")
                .map { it.toInt() }
        }

    val setOfSurfaceSides = sideOfCubes(cubePositions)

    println(setOfSurfaceSides.size)
    // Solution 3364

    val xMin = cubePositions.minOf { it[0] } - 1
    val xMax = cubePositions.maxOf { it[0] } + 1
    val yMin = cubePositions.minOf { it[1] } - 1
    val yMax = cubePositions.maxOf { it[1] } + 1
    val zMin = cubePositions.minOf { it[2] } - 1
    val zMax = cubePositions.maxOf { it[2] } + 1

//    println("$xMin $xMax $yMin $yMax $zMin $zMax")

    val fillCubePositions = getSurroundingCubes(cubePositions, xMin, yMin, zMin, xMax, yMax, zMax)

    val fillCubesSides = sideOfCubes(fillCubePositions)

    val outsideSides = 2 * (xMax - xMin + 1) * (yMax - yMin + 1) +
            2 * (xMax - xMin + 1) * (zMax - zMin + 1) +
            2 * (zMax - zMin + 1) * (yMax - yMin + 1)

    println(fillCubesSides.size - outsideSides)
    // Solution 2006
}

private fun sideOfCubes(cubePositions: List<List<Int>>): MutableSet<SideOfCube> {
    val setOfSurfaceSides = mutableSetOf<SideOfCube>()
    cubePositions.forEach { position ->
        val listOfSides = getCubeSides(position[0], position[1], position[2])
        listOfSides.forEach { side ->
            val adj = adjSide(side)
            if (adj in setOfSurfaceSides) setOfSurfaceSides.remove(adj)
            else setOfSurfaceSides.add(side)
        }
    }
    return setOfSurfaceSides
}

private fun getSurroundingCubes(cubePositions: List<List<Int>>, xMin: Int, yMin: Int, zMin: Int, xMax: Int, yMax: Int, zMax: Int): List<List<Int>> {
    val fillCubePositions = mutableListOf<List<Int>>()
    val queue = ArrayDeque<List<Int>>()

    val start = listOf(xMin, yMin, zMin)
    queue.addLast(start)
    fillCubePositions.add(start)

    fun addCubeToList(cp: List<Int>) {
        if (cp !in cubePositions && cp !in fillCubePositions) {
            fillCubePositions.add(cp)
            queue.addLast(cp)
        }
    }

    while(queue.isNotEmpty()) {
        val cubePos = queue.removeFirst()

        if (cubePos[0] - 1  >= xMin) {
            val cp = listOf(cubePos[0] - 1, cubePos[1], cubePos[2])
            addCubeToList(cp)
        }
        if (cubePos[0] + 1  <= xMax) {
            val cp = listOf(cubePos[0] + 1, cubePos[1], cubePos[2])
            addCubeToList(cp)
        }
        if (cubePos[1] - 1  >= yMin) {
            val cp = listOf(cubePos[0], cubePos[1] - 1, cubePos[2])
            addCubeToList(cp)
        }
        if (cubePos[1] + 1  <= yMax) {
            val cp = listOf(cubePos[0], cubePos[1] + 1, cubePos[2])
            addCubeToList(cp)
        }
        if (cubePos[2] - 1  >= zMin) {
            val cp = listOf(cubePos[0], cubePos[1], cubePos[2] - 1)
            addCubeToList(cp)
        }
        if (cubePos[2] + 1  <= zMax) {
            val cp = listOf(cubePos[0], cubePos[1], cubePos[2] + 1)
            addCubeToList(cp)
        }
    }

    return fillCubePositions.toList()
}

fun getCubeSides(x: Int, y: Int, z:Int): List<SideOfCube> {
    val listOfSideCubes = mutableListOf<SideOfCube>()
    listOfSideCubes.add(SideOfCube(x, y, z, -3))
    listOfSideCubes.add(SideOfCube(x, y, z + 1, 3))
    listOfSideCubes.add(SideOfCube(x, y, z, -2))
    listOfSideCubes.add(SideOfCube(x, y + 1, z, 2))
    listOfSideCubes.add(SideOfCube(x, y, z, -1))
    listOfSideCubes.add(SideOfCube(x + 1, y, z, 1))

    return listOfSideCubes.toList()
}

fun adjSide(side: SideOfCube): SideOfCube {
    return SideOfCube(side.x, side.y, side.z, -1 * side.direction)
}
