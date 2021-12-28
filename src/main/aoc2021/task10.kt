import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val INPUT_FILE_PATH = "src/main/resources/input_task_10.txt"

private val codeCharMapping = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)
private val codeOpeningChars = codeCharMapping.keys
private val errorScoreMap = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

private val autocompleteScoreMap = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4,
)

fun main() {
    val navigationSubsystemCode = File(INPUT_FILE_PATH).readLines()

    // Task 10.1
    calcSyntaxErrorScore(navigationSubsystemCode)
        .also { println("Task 10.1: $it") }

    // Task 10.2
    calcAutocompleteScore(navigationSubsystemCode)
        .also { println("Task 10.2: $it") }
}

fun calcSyntaxErrorScore(navigationSubsystemCode: List<String>): Long =
    navigationSubsystemCode.fold(0) { acc, s -> acc + calcSyntaxErrorScore(s.toList()) }

fun calcAutocompleteScore(navigationSubsystemCode: List<String>): Long = navigationSubsystemCode
    .map { calcAutocompleteScore(it.toList()) }
    .filter { it > 0 }
    .calculateMedian()


private tailrec fun calcSyntaxErrorScore(code: List<Char>, stack: List<Char> = emptyList()): Long =
    when {
        code.isEmpty() -> 0
        code.first() in codeOpeningChars ->
            calcSyntaxErrorScore(code.drop(1), listOf(code.first()) + stack)
        code.first() == codeCharMapping[stack.first()] ->
            calcSyntaxErrorScore(code.drop(1), stack.drop(1))
        else -> errorScoreMap[code.first()]?.toLong() ?: 0L
    }

private tailrec fun calcAutocompleteScore(code: List<Char>, stack: List<Char> = emptyList()): Long =
    when {
        code.isEmpty() -> stack.mapNotNull { autocompleteScoreMap[codeCharMapping[it]] }
            .fold(0L) { acc, i -> acc * 5 + i }
        code.first() in codeOpeningChars ->
            calcAutocompleteScore(code.drop(1), listOf(code.first()) + stack)
        code.first() == codeCharMapping[stack.first()] ->
            calcAutocompleteScore(code.drop(1), stack.drop(1))
        else -> 0
    }

private fun List<Long>.calculateMedian(): Long = this.sorted().let {
    if (size % 2 != 0) it[size / 2]
    else (it[size / 2] + it[(size / 2) - 1]) / 2
}