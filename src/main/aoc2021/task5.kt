import java.io.File
import kotlin.math.max
import kotlin.math.min

private const val INPUT_FILE_PATH = "src/main/resources/input_task_5.txt"

private data class Point(
    val x: Int,
    val y: Int
)

private data class Range(
    val startPoint: Point,
    val endPoint: Point,
) {
    fun getContainingPoints(allowVertical: Boolean): List<Point> {
        return when {
            startPoint.x == endPoint.x -> (min(startPoint.y, endPoint.y)..max(startPoint.y, endPoint.y)).map {
                Point(startPoint.x, it)
            }
            startPoint.y == endPoint.y -> (min(startPoint.x, endPoint.x)..max(startPoint.x, endPoint.x)).map {
                Point(it, startPoint.y)
            }
            !allowVertical -> emptyList()
            else -> {
                val xRange = (min(startPoint.x, endPoint.x)..max(startPoint.x, endPoint.x))
                    .toList()
                    .let { if (startPoint.x > endPoint.x) it.reversed() else it }

                val yRange = (min(startPoint.y, endPoint.y)..max(startPoint.y, endPoint.y))
                    .toList()
                    .let { if (startPoint.y > endPoint.y) it.reversed() else it }

                xRange.zip(yRange) { x, y -> Point(x, y) }
            }
        }
    }
}

fun main() {
    // Task 5.1
    File(INPUT_FILE_PATH).useLines { lines ->
        calculateNumberOfDangerousHydrothermalVents(lines, false)
    }.also { println("Task 5.1: $it") }

    // Task 5.2
    File(INPUT_FILE_PATH).useLines { lines ->
        calculateNumberOfDangerousHydrothermalVents(lines, true)
    }.also { println("Task 5.2: $it") }
}

fun calculateNumberOfDangerousHydrothermalVents(hydroThermalVentInput: Sequence<String>, allowVertical: Boolean): Int =
    calculateDangerousHydrothermalVents(hydroThermalVentInput.map { it.parseToRange() }, allowVertical).count()

private fun calculateDangerousHydrothermalVents(
    hydroThermalVents: Sequence<Range>,
    allowVertical: Boolean
): List<Point> {
    val nearbyMap = mutableMapOf<Point, Int>()
    hydroThermalVents.forEach { range ->
        range.getContainingPoints(allowVertical).forEach { point ->
            nearbyMap[point] = nearbyMap.getOrDefault(point, 0) + 1
        }
    }
    return nearbyMap.filter { (_, vents) -> vents > 1 }.keys.toList()
}

/*
// this works but is kind of inefficient because it copies the map for every range
private fun calculateDangerousHydrothermalVentsInternPure(
    hydroThermalVents: Sequence<Range>,
    allowVertical: Boolean
): List<Point> {
    return hydroThermalVents.fold(emptyMap<Point, Int>()) { acc, e ->
        // this copies the original map acc
        acc + e.getContainingPoints(allowVertical).map { it to acc.getOrDefault(it, 0) + 1 }
    }.filter { (_, vents) -> vents > 1 }.keys.toList()
}
*/

private fun String.parseToRange(): Range {
    val points = this.split(" -> ").map { it.parseToPoint() }
    return Range(points[0], points[1])
}

private fun String.parseToPoint(): Point {
    val coordinates = this.split(",").map { it.toInt() }
    return Point(coordinates[0], coordinates[1])
}
