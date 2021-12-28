import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_12.txt"

private data class CaveNode(
    val id: String,
    val isSmall: Boolean = isCaveNodeSmall(id)
)

private data class CaveEdge(
    val startNode: CaveNode,
    val endNode: CaveNode
)

private const val START_NODE_ID = "start"
private const val END_NODE_ID = "end"

fun main() {
    val caveEdges = File(INPUT_FILE_PATH).readLines()

    // Task 12.1
    calcNumberOfPaths(caveEdges)
        .also { println("Task 12.1: $it") }

    // Task 12.2
    calcNumberOfPaths(caveEdges, 1)
        .also { println("Task 12.2: $it") }
}

fun calcNumberOfPaths(caveConnections: List<String>, smallCaveRevisitLimit: Int = 0): Int = calcNumberOfPathsIntern(
    caveConnections.flatMap { it.parseCaveConnections() }.groupBy { it.startNode.id },
    smallCaveRevisitLimit
)

private fun calcNumberOfPathsIntern(
    caveConnections: Map<String, List<CaveEdge>>,
    smallCaveRevisitLimit: Int,
    currentPath: List<CaveEdge> = emptyList(),
): Int {
    val currentEdge = currentPath.lastOrNull()?.endNode?.id ?: START_NODE_ID

    // if we are at end node, we found one path from start to end
    if (currentEdge == END_NODE_ID) return 1

    // don't allow going back to start
    if (currentEdge == START_NODE_ID && currentPath.isNotEmpty()) return 0

    // check for small-cave revisits
    val smallCaveRevisit = isCaveNodeSmall(currentEdge) &&
            currentEdge in currentPath.map { it.startNode.id }
    if (smallCaveRevisit && smallCaveRevisitLimit <= 0) return 0

    // travel outgoing edges recusive
    val outgoingEdges = caveConnections[currentEdge] ?: return 0
    return outgoingEdges.sumOf {
        calcNumberOfPathsIntern(
            caveConnections,
            smallCaveRevisitLimit - (if (smallCaveRevisit) 1 else 0),
            currentPath + it
        )
    }
}

private fun String.parseCaveConnections(): List<CaveEdge> =
    this.split('-').let {
        listOf(
            CaveEdge(CaveNode(it[0]), CaveNode(it[1])),
            CaveEdge(CaveNode(it[1]), CaveNode(it[0]))
        )
    }

private fun isCaveNodeSmall(caveNodeId: String): Boolean =
    caveNodeId == caveNodeId.lowercase()