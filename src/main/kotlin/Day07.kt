import java.io.File

data class Dir(val name: String, val parent: Dir? = null) {
    val files: MutableList<Fil> = mutableListOf()
    val dirs: MutableList<Dir> = mutableListOf()
    var size = 0
}
data class Fil(val name: String, val size: Int)

fun main() {
    val lines = File("src/main/kotlin/input07.txt")
        .readLines()
        .asSequence()

    val printout = lines
        .filter { it != "$ ls" }
        .map { it.replace("$ ", "") }
        .map { it.split(" ") }

    val rootDir = Dir("/")
    var position = rootDir
    for (line in printout) {
        when (line[0]) {
            "cd" -> {
                position = when (line[1]) {
                    "/" -> rootDir
                    ".." -> position.parent!!
                    else -> position.dirs.first { it.name == line[1] }
                }
            }
            "dir" -> {
                position.dirs.add(Dir(line[1], position))
            }
            else -> {
                val size = line[0].toInt()
                position.files.add(Fil(line[1], size))
                position.size += size
            }
        }
    }
    addDirSizes(rootDir)

    println(addLess100K(rootDir))
    // Solution 1297159

    val freeSpace = 70000000 - rootDir.size
    val neededSpace = 30000000 - freeSpace
    printNeeded(rootDir, neededSpace)
}

fun addDirSizes(dir: Dir): Int {
    for (d in dir.dirs) {
        dir.size += addDirSizes(d)
    }
    return dir.size
}

fun addLess100K(dir: Dir): Int {
    var count = 0
    for (d in dir.dirs) count += addLess100K(d)

    return if (dir.size < 100000) count + dir.size else count
}

fun printNeeded(dir: Dir, neededSpace: Int) {
    for (d in dir.dirs) printNeeded(d, neededSpace)
    if (dir.size >= neededSpace) {
        println("${dir.size} $dir")
    }
}

