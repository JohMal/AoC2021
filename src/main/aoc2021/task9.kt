import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val INPUT_FILE_PATH = "src/main/resources/input_task_9.txt"

fun main() {
    val heightMapInput = File(INPUT_FILE_PATH).readLines()

    // Task 9.1
    findTotalRiskLevelOfHeightMap(heightMapInput)
        .also { println("Task 9.1: $it") }

    // Task 9.2

}

fun findTotalRiskLevelOfHeightMap(heightMapInput: List<String>): Int =
    findRiskLevelOfHeightMap(heightMapInput.map { line -> line.toList().map { Character.getNumericValue(it) } })

private fun findRiskLevelOfHeightMap(heightMap: List<List<Int>>): Int {
    val res = findLowPoints(heightMap)
    return res.sumOf { it + 1 }
}


private fun findLowPoints(heightMap: List<List<Int>>): List<Int> {
    // an empty heightMap has no low points
    val numberOfColumns = heightMap.firstOrNull()?.size ?: return emptyList()

    // adding Int.MaxValue to all 4 sides to allow easier windowing at the edges
    val extendedHeightMap: List<List<Int>> = listOf(List(numberOfColumns + 2) { Int.MAX_VALUE }) +
            heightMap.map { listOf(Int.MAX_VALUE) + it + listOf(Int.MAX_VALUE) } +
            listOf(List(numberOfColumns + 2) { Int.MAX_VALUE })

    // perform 2D sliding window with size of 3,3 to find all where the center is the smallest point in window
    return extendedHeightMap.windowed(3).map { rows ->
        val combinedList = (rows[0] zip rows[1]).zip(rows[2]) { (a, b), c -> listOf(a, b, c) }
        combinedList.windowed(3)
            .filter { window ->
                listOf(window[0][1], window[1][0], window[1][2], window[2][1])
                    .all { window[1][1] < it }
            }.map { it[1][1] }
    }.flatten()
}