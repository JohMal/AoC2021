import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_4.txt"

private data class BingoGameState(
    val drawSequence: List<Int> = emptyList(),
    val boards: List<BingoBoard>,
)

data class BingoBoard(
    val fields: List<List<BingoField>>,
    val bingo: Boolean = false,
    val lastAppliedNumber: Int = 0,
) {
    val score: Int
        get() = fields.flatten().filter { !it.checked }.sumOf { it.number } * lastAppliedNumber

    fun applyDraw(drawedNumber: Int): BingoBoard {
        val appliedFields = fields.map { row ->
            row.map { if (it.number == drawedNumber) it.copy(checked = true) else it }
        }

        return BingoBoard(appliedFields, checkBoardForBingo(appliedFields), drawedNumber)
    }
}

data class BingoField(
    val number: Int,
    val checked: Boolean = false,
)

fun main() {
    val bingoInput = File(INPUT_FILE_PATH).readText()

    // Task 4.1
    findFirstWinningBoard(bingoInput)?.let {
        println("Task 4.1: ${it.score}")
    }

    // Task 4.2
    findLastWinningBoard(bingoInput)?.let {
        println("Task 4.2: ${it.score}")
    }
}

fun findFirstWinningBoard(bingoInput: String): BingoBoard? =
    findFirstWinningBoard(bingoInput.parseToBingoGameState())

fun findLastWinningBoard(bingoInput: String): BingoBoard? =
    findLastWinningBoard(bingoInput.parseToBingoGameState())

private tailrec fun findFirstWinningBoard(gameState: BingoGameState): BingoBoard? {
    val winningBoard = gameState.boards.firstOrNull { it.bingo }
    return when {
        winningBoard != null -> winningBoard
        gameState.drawSequence.isEmpty() -> null
        else -> findFirstWinningBoard(
            BingoGameState(gameState.drawSequence.drop(1),
                gameState.boards.map { it.applyDraw(gameState.drawSequence.first()) })
        )
    }
}

private tailrec fun findLastWinningBoard(gameState: BingoGameState): BingoBoard? {
    val wonBoards = gameState.boards.filter { it.bingo }
    val remainingBoards = gameState.boards - wonBoards
    return when {
        remainingBoards.isEmpty() && wonBoards.isEmpty() -> null
        remainingBoards.isEmpty() -> wonBoards.last()
        else -> findLastWinningBoard(
            BingoGameState(gameState.drawSequence.drop(1),
                remainingBoards.map { it.applyDraw(gameState.drawSequence.first()) })
        )
    }
}

private fun String.parseToBingoGameState(): BingoGameState {
    val splittedInput = this.split("\n\n")
    val drawSequence = splittedInput.first().split(",").map { it.toInt() }
    val boards = splittedInput.drop(1).map { it.parseToBingoBoard() }
    return BingoGameState(drawSequence, boards)
}

private fun String.parseToBingoBoard(): BingoBoard {
    val lines = this.split("\n")
        .map { line -> line.split(" ").mapNotNull { it.toIntOrNull()?.let { number -> BingoField(number) } } }
        .filter { it.isNotEmpty() }
    return BingoBoard(lines)
}

private fun checkBoardForBingo(fields: List<List<BingoField>>): Boolean {
    val columns = fields.indices.map { colId -> fields.map { it[colId] } }

    return when {
        fields.any { line -> line.all { it.checked } } -> true // bingo in line
        columns.any { column -> column.all { it.checked } } -> true // bingo in column
        else -> false
    }
}

