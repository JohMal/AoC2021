import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/task1/input_task_1.txt"

fun main() {
    // Task 1.1
    File(INPUT_FILE_PATH).useLines { lines ->
        calculateSonarIncreasingDepth(lines.map { it.toInt() })
    }.also { println("Task 1.1: $it") }

    // Task 1.2
    File(INPUT_FILE_PATH).useLines { lines ->
        calculateSonarIncreasingDepth(lines.map { it.toInt() }, 3)
    }.also { println("Task 1.2: $it") }
}

fun calculateSonarIncreasingDepth(sonarDepths: Sequence<Int>, sumCount: Int = 1): Int =
    sonarDepths.windowed(sumCount, 1)
        .map { it.sum() }
        .windowed(2, 1)
        .count { (first, second) -> second > first }