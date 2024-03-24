import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_11.txt"

private lateinit var octopusEnergyMap: Array<IntArray>

fun main() {
    val octopusStartEnergyMap = File(INPUT_FILE_PATH).readLines()

    // Task 11.1
    calcNumberOfFlashes(octopusStartEnergyMap, 100)
        .also { println("Task 11.1: $it") }

    // Task 11.2
    calcAllFlashDay(octopusStartEnergyMap)
        .also { println("Task 11.2: $it") }
}

fun calcNumberOfFlashes(octopusEnergyMap: List<String>, numberOfDays: Int): Int =
    calcNumberOfFlashes(octopusEnergyMap.map { it.parseOctopusEnergyLine() }.toTypedArray(), numberOfDays)

fun calcAllFlashDay(octopusEnergyMap: List<String>): Int =
    calcAllFlashDay(octopusEnergyMap.map { it.parseOctopusEnergyLine() }.toTypedArray())

private fun calcNumberOfFlashes(octopusEnergyMapInitial: Array<IntArray>, numberOfDays: Int): Int {
    octopusEnergyMap = octopusEnergyMapInitial
    var numberOfFlashes = 0
    for (i in 0 until numberOfDays) {
        numberOfFlashes += performSingleDayTransition()
    }
    return numberOfFlashes
}

private fun calcAllFlashDay(octopusEnergyMapInitial: Array<IntArray>): Int {
    octopusEnergyMap = octopusEnergyMapInitial
    val octopusCount = octopusEnergyMap.sumOf { it.count() }
    var i = 0
    do {
        val flashCount = performSingleDayTransition()
        i++
    } while (flashCount < octopusCount)
    return i
}

private fun performSingleDayTransition(): Int {
    // daily transition
    for (i in octopusEnergyMap.indices) {
        for (j in octopusEnergyMap[i].indices) {
            addOneAndPerformFlashIfNeeded(Pair(i, j))
        }
    }

    // reset after flash and count flashes on current day
    var numberOfFlashes = 0
    for (i in octopusEnergyMap.indices) {
        for (j in octopusEnergyMap[i].indices) {
            if (octopusEnergyMap[i][j] > 9) {
                numberOfFlashes += 1
                octopusEnergyMap[i][j] = 0
            }
        }
    }
    return numberOfFlashes
}

private fun addOneAndPerformFlashIfNeeded(index: Pair<Int, Int>) {
    val (x, y) = index
    octopusEnergyMap[x][y] = octopusEnergyMap[x][y] + 1
    if (octopusEnergyMap[x][y] == 10) {
        if (x > 0)
            addOneAndPerformFlashIfNeeded(Pair(x - 1, y))
        if (x < octopusEnergyMap.lastIndex)
            addOneAndPerformFlashIfNeeded(Pair(x + 1, y))
        if (y > 0)
            addOneAndPerformFlashIfNeeded(Pair(x, y - 1))
        if (y < octopusEnergyMap[x].lastIndex)
            addOneAndPerformFlashIfNeeded(Pair(x, y + 1))
        if (x > 0 && y > 0)
            addOneAndPerformFlashIfNeeded(Pair(x - 1, y - 1))
        if (x > 0 && y < octopusEnergyMap[x].lastIndex)
            addOneAndPerformFlashIfNeeded(Pair(x - 1, y + 1))
        if (x < octopusEnergyMap.lastIndex && y > 0)
            addOneAndPerformFlashIfNeeded(Pair(x + 1, y - 1))
        if (x < octopusEnergyMap.lastIndex && y < octopusEnergyMap[x].lastIndex)
            addOneAndPerformFlashIfNeeded(Pair(x + 1, y + 1))
    }
}

private fun String.parseOctopusEnergyLine(): IntArray = this
    .toCharArray()
    .map(Character::getNumericValue)
    .toIntArray()