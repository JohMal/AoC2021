import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_13.txt"

private data class DotPosition(
    val x: Int,
    val y: Int
)

private enum class FoldDirection { UP, LEFT }

private data class FoldInstruction(
    val foldDirection: FoldDirection,
    val foldLine: Int
)

fun main() {
    val inputPaper = File(INPUT_FILE_PATH).readLines()

    // Task 13.1
    calcNumberOfVisibleDots(inputPaper, 1)
        .also { println("Task 13.1: $it") }

    // Task 13.2
    calcNumberOfVisibleDots(inputPaper)
        .also { println("Task 13.2: $it") }
}

fun calcNumberOfVisibleDots(inputPaper: List<String>, numberOfFolds: Int = -1): Int {
    // parse input
    val separatorIndex = inputPaper.indexOf("")
    val dottedPoints = inputPaper.subList(0, separatorIndex)
        .map { it.parseDotPosition() }
        .toSet()
    val foldInstructions = inputPaper.subList(separatorIndex + 1, inputPaper.size)
        .map { it.parseFoldInstruction() }
        .let { if (numberOfFolds < 0) it else it.take(numberOfFolds) }

    // calculate result
    return calcNumberOfVisibleDots(dottedPoints, foldInstructions)
}

private fun calcNumberOfVisibleDots(
    dottedPoints: Set<DotPosition>,
    foldInstructions: List<FoldInstruction>
): Int = foldInstructions.fold(dottedPoints) { acc, instruction ->
    when (instruction.foldDirection) {
        FoldDirection.UP -> acc.foldUp(instruction.foldLine)
        FoldDirection.LEFT -> acc.foldLeft(instruction.foldLine)
    }
}.also { printPaperState(it) }
    .count()

private fun Set<DotPosition>.foldLeft(foldLine: Int): Set<DotPosition> = this.map {
    if (it.x <= foldLine) it
    else DotPosition(foldLine - (it.x - foldLine), it.y)
}.toSet()

private fun Set<DotPosition>.foldUp(foldLine: Int): Set<DotPosition> = this.map {
    if (it.y <= foldLine) it
    else DotPosition(it.x, foldLine - (it.y - foldLine))
}.toSet()

private fun String.parseDotPosition(): DotPosition =
    this.split(',').let { DotPosition(it[0].toInt(), it[1].toInt()) }

private fun String.parseFoldInstruction(): FoldInstruction {
    val instr = this.split(" ").last().split('=')
    return FoldInstruction(
        foldDirection = if (instr[0] == "x") FoldDirection.LEFT else FoldDirection.UP,
        foldLine = instr[1].toInt()
    )
}

private fun printPaperState(dottedPoints: Set<DotPosition>) {
    val dottedPointMap = dottedPoints.associateWith { true }
    val maxX = dottedPoints.maxOf { it.x }
    val maxY = dottedPoints.maxOf { it.y }

    println()
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            print(if (dottedPointMap[DotPosition(x, y)] == true) "# " else ". ")
        }
        println()
    }
    println()
}