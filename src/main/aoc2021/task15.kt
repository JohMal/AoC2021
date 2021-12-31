import java.io.File
import java.util.*

private const val INPUT_FILE_PATH = "src/main/resources/input_task_15.txt"

private typealias RiskMap = Array<Array<GraphNode>>

private data class GraphNode(
    val row: Int, val column: Int, val weight: Int, var distance: Int = Int.MAX_VALUE
)

private class GraphNodeDistanceComparator {
    companion object : Comparator<GraphNode> {
        override fun compare(a: GraphNode, b: GraphNode): Int = a.distance.compareTo(b.distance)
    }
}

fun main() {
    val riskMapInput = File(INPUT_FILE_PATH).readLines()

    // Task 15.1
    calcLowestRiskPath(riskMapInput).also { println("Task 15.1: $it") }

    // Task 15.2
    calcLowestRiskPathExtended(riskMapInput).also { println("Task 15.2: $it") }
}

fun calcLowestRiskPath(inputRiskMap: List<String>): Int {
    return calcLowestRiskPathIntern(
        inputRiskMap
            .mapIndexed { index: Int, s: String -> s.parseRiskMapRow(index) }
            .toTypedArray()
    )
}

fun calcLowestRiskPathExtended(inputRiskMap: List<String>): Int {
    return calcLowestRiskPathIntern(
        inputRiskMap
            .mapIndexed { index: Int, s: String -> s.parseRiskMapRow(index) }
            .toTypedArray()
            .extend()
    )
}

// simple, unoptimized version of the Dijkstra-Algorithm
private fun calcLowestRiskPathIntern(riskMap: RiskMap): Int {
    val waitingQueue: PriorityQueue<GraphNode> = PriorityQueue(GraphNodeDistanceComparator)

    // init state
    riskMap[0][0].distance = 0
    waitingQueue.add(riskMap[0][0])

    while (!waitingQueue.isEmpty()) {
        // find current min distance node
        val currentNode = waitingQueue.poll()

        // update neighbours
        updateNodeDistanceIfNeeded(currentNode.row - 1, currentNode.column, currentNode.distance, riskMap, waitingQueue)
        updateNodeDistanceIfNeeded(currentNode.row + 1, currentNode.column, currentNode.distance, riskMap, waitingQueue)
        updateNodeDistanceIfNeeded(currentNode.row, currentNode.column - 1, currentNode.distance, riskMap, waitingQueue)
        updateNodeDistanceIfNeeded(currentNode.row, currentNode.column + 1, currentNode.distance, riskMap, waitingQueue)
    }
    // since bottom right is the target, we return its distance
    return riskMap.last().last().distance
}

private fun updateNodeDistanceIfNeeded(
    rowIndex: Int, columnIndex: Int, currentDistance: Int, riskMap: RiskMap, waitingQueue: PriorityQueue<GraphNode>
) {
    riskMap.getOrNull(rowIndex)?.getOrNull(columnIndex)?.let {
        if (currentDistance + it.weight < it.distance) {
            it.distance = currentDistance + it.weight
            // remove and reinsert to update priority with new distance
            waitingQueue.remove(it)
            waitingQueue.add(it)
        }
    }
}

private fun RiskMap.extend(): RiskMap = RiskMap(this.size * 5) { rowIndex ->
    val packRow = rowIndex / this.size
    val indexInRowPack = rowIndex % this.size
    Array(this[indexInRowPack].size * 5) { columnIndex ->
        val packColumn = columnIndex / this[indexInRowPack].size
        val indexInColumnPack = columnIndex % this[indexInRowPack].size
        val weight = (this[indexInRowPack][indexInColumnPack].weight + packRow + packColumn).let {
            // values > 9 are starting back from 1
            // since the max it can be extended by is 8 (4 + 4), it cannot wrap twice
            if (it > 9) it - 9 else it
        }
        GraphNode(rowIndex, columnIndex, weight)
    }
}

private fun String.parseRiskMapRow(rowIndex: Int): Array<GraphNode> =
    this.toCharArray().mapIndexed { columnIndex: Int, c: Char ->
        GraphNode(row = rowIndex, column = columnIndex, weight = Character.getNumericValue(c))
    }.toTypedArray()

