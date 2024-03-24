import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val INPUT_FILE_PATH = "src/main/resources/input_task_7.txt"

enum class FuelCostStrategy { CONSTANT, INC }

fun main() {
    val crapPositionInput = File(INPUT_FILE_PATH).readLines()

    // Task 7.1
    calculateMinCrapAlignmentFuelCosts(crapPositionInput.first(), FuelCostStrategy.CONSTANT)
        .also { println("Task 7.1: $it") }

    // Task 7.2
    calculateMinCrapAlignmentFuelCosts(crapPositionInput.first(), FuelCostStrategy.INC)
        .also { println("Task 7.2: $it") }
}

fun calculateMinCrapAlignmentFuelCosts(
    crapPositionInput: String,
    fuelCostStrategy: FuelCostStrategy = FuelCostStrategy.CONSTANT
): Int = crapPositionInput.split(",").map { it.toInt() }.let { crapPositions ->
    when (fuelCostStrategy) {
        FuelCostStrategy.CONSTANT -> calculateMinCrapAlignmentFuelCostsConstant(crapPositions)
        FuelCostStrategy.INC -> calculateMinCrapAlignmentFuelCostsInc(crapPositions)
    }
}

private fun calculateMinCrapAlignmentFuelCostsConstant(crapPositionInput: List<Int>): Int {
    val median = crapPositionInput.calculateMedian()
    return crapPositionInput.fold(0) { acc, i -> acc + abs(i - median) }
}

private fun calculateMinCrapAlignmentFuelCostsInc(crapPositionInput: List<Int>): Int {
    val min = crapPositionInput.minOrNull() ?: 0
    val max = crapPositionInput.maxOrNull() ?: 0

    return List(max - min) { crapPositionInput }
        .mapIndexed { index: Int, list: List<Int> ->
            list.fold(0) { acc, i -> acc + calculateSumTo(abs(i - index)) }
        }.minOrNull()!!
}

private fun List<Int>.calculateMedian(): Int = this.sorted().let {
    if (size % 2 != 0) it[size / 2]
    else (it[size / 2] + it[(size / 2) - 1]) / 2
}

private fun calculateSumTo(n: Int) = (n * (n + 1)) / 2