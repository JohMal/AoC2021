import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_6.txt"

fun main() {
    val initialFishStateInput = File(INPUT_FILE_PATH).readLines()

    // Task 6.1
    calculateNumberOfLanternFish(initialFishStateInput.first(), 80)
        .also { println("Task 6.1: $it") }

    // Task 6.2
    calculateNumberOfLanternFish(initialFishStateInput.first(), 256)
        .also { println("Task 6.2: $it") }
}

fun calculateNumberOfLanternFish(initalStateInput: String, numberOfSimulationDays: Int): Long {
    val initialState = initalStateInput
        .split(",")
        .map { it.toInt() }
        .groupBy { it }
        .mapValues { it.value.count().toLong() }

    return executeFishStateTransition(numberOfSimulationDays, initialState)
        .toList().sumOf { it.second }
}

private tailrec fun executeFishStateTransition(simulationDaysLeft: Int, fishState: Map<Int, Long>): Map<Int, Long> {
    return if (simulationDaysLeft == 0) fishState else {
        val nextState = fishState.toList().groupBy { if (it.first == 0) 6 else it.first - 1 }
            .mapValues { value -> value.value.sumOf { it.second } } +
                mapOf(8 to fishState.getOrDefault(0, 0))

        executeFishStateTransition(simulationDaysLeft - 1, nextState)
    }
}