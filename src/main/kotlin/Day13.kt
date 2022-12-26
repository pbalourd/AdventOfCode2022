import java.io.File
import java.lang.Exception

fun main() {
    val lines = File("src/main/kotlin/input13.txt").readLines()
        .filter { it.isNotBlank() }

    val trees = mutableListOf<ListNode>()
    for (i in lines.indices) {
        val line = """(\d+|]|\[)""".toRegex()
            .findAll(lines[i])
            .map { it.value }
            .toMutableList()

        trees.add(createNodesTree(line))
    }

    var countRightOrder = 0
    for (i in trees.indices step 2) {
        if (trees[i] < trees[i+1]) countRightOrder += i/2 + 1
    }
    println(countRightOrder)
    // Solution 5843

    val node2 = ListNode(listOf(ListNode(listOf(IntNode(2)))))
    val node6 = ListNode(listOf(ListNode(listOf(IntNode(6)))))
    trees.add(node2)
    trees.add(node6)
    trees.sort()
    println( (trees.indexOf(node2) + 1) * (trees.indexOf(node6) + 1))
    // Solution 26289
}

sealed interface Node: Comparable<Node>

data class IntNode(val value: Int): Node {
    override fun compareTo(other: Node): Int {
        return when (other) {
            is IntNode -> value - other.value
            is ListNode -> ListNode(listOf(IntNode(value))).compareTo(other)
        }
    }
}

data class ListNode(val listNode: List<Node>): Node {
    override fun compareTo(other: Node): Int {
        return when (other) {
            is ListNode -> {
                listNode.zip(other.listNode, Node::compareTo)
                    .firstOrNull { it != 0 } ?: (listNode.size - other.listNode.size)
            }
            is IntNode -> {
                this.compareTo(ListNode(listOf(IntNode(other.value))))
            }
        }
    }
}

fun createNodesTree(input: MutableList<String>): ListNode {
     if (input.removeAt(0) != "[") throw Exception("Wrong input")

    val newNodeList = mutableListOf<Node>()
    while (input[0] != "]") {
        if (input[0] == "[") {
            newNodeList.add(createNodesTree(input))
        }
        else {
            newNodeList.add(IntNode(input.removeAt(0).toInt()))
        }
    }
    input.removeAt(0)
    return ListNode(newNodeList.toList())
}

