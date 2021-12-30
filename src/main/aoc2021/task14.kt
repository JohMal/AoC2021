import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_14.txt"

private typealias PairInsertionRules = Map<String, Char>
private typealias PairInsertionRuleEntry = Pair<String, Char>

fun main() {
    val polymerInput = File(INPUT_FILE_PATH).readLines()

    // Task 14.1
    calcExpandedPolymerScore(polymerInput, 10)
        .also { println("Task 14.1: $it") }

    // Task 14.2
    calcExpandedPolymerScore(polymerInput, 40)
        .also { println("Task 14.2: $it") }
}

fun calcExpandedPolymerScore(polymerInput: List<String>, numberOfSteps: Int): Long {
    // parse input
    val polymerTemplate = polymerInput.first()
    val pairInsertionRules = polymerInput.drop(2).associate { rule ->
        rule.parsePairInsertionRule().let { it.first to it.second }
    }

    // the simple method is fine for small numberOfSteps but grows exponential, so we use the optimized version for large numberOfSteps
    return if (numberOfSteps <= 10) calcExpandedPolymerScore(polymerTemplate, pairInsertionRules, numberOfSteps)
    else calcExpandedPolymerScoreOptimized(polymerTemplate, pairInsertionRules, numberOfSteps)
}

private fun calcExpandedPolymerScore(
    polymerTemplate: String,
    pairInsertionRules: PairInsertionRules,
    numberOfSteps: Int
): Long {
    // expand polymer
    val expandedPolymer = expandPolymer(polymerTemplate, pairInsertionRules, numberOfSteps)

    // calculate score
    val characterGroups = expandedPolymer.groupBy { it }.mapValues { it.value.count().toLong() }
    val minCharacterCount = characterGroups.minOf { it.value }
    val maxCharacterCount = characterGroups.maxOf { it.value }

    return maxCharacterCount - minCharacterCount
}

private tailrec fun expandPolymer(polymer: String, pairInsertionRules: PairInsertionRules, numberOfSteps: Int): String =
    if (numberOfSteps == 0) polymer
    else {
        val expandedPolymer = polymer.windowed(2).fold("${polymer[0]}") { acc, e ->
            val insertChar = pairInsertionRules[e] ?: ""
            "$acc$insertChar${e[1]}"
        }
        expandPolymer(expandedPolymer, pairInsertionRules, numberOfSteps - 1)
    }

private fun calcExpandedPolymerScoreOptimized(
    polymer: String,
    pairInsertionRules: PairInsertionRules,
    numberOfSteps: Int
): Long {
    // expand polymer pairs
    val pairs = polymer.windowed(2).groupBy { it }
        .mapValues { it.value.count().toLong() }
        .toList()
    val expandedPairs = expandPolymerPairs(pairs, pairInsertionRules, numberOfSteps)

    // calculate score
    val characterGroups = expandedPairs
        .groupBy { it.first[0] } // group every pair by first character
        .mapValues {
            it.value.sumOf { (_, occurrences) -> occurrences } +
                    if (it.key == polymer.last()) 1 else 0 // the last character of polymer is missing because of the grouping above
        }
    val minCharacterCount = characterGroups.minOf { it.value }
    val maxCharacterCount = characterGroups.maxOf { it.value }

    return maxCharacterCount - minCharacterCount
}

private tailrec fun expandPolymerPairs(
    polymerPairs: List<Pair<String, Long>>,
    pairInsertionRules: PairInsertionRules,
    numberOfSteps: Int
): List<Pair<String, Long>> =
    if (numberOfSteps == 0) polymerPairs
    else {
        val expandedPolymerPairs = polymerPairs.flatMap { (pair, occurrences) ->
            val insertChar = pairInsertionRules[pair]
            return@flatMap if (insertChar == null) listOf(Pair(pair, occurrences)) // keep pair if no rule
            else listOf( // replace current pair with two new pairs
                Pair("${pair[0]}$insertChar", occurrences),
                Pair("$insertChar${pair[1]}", occurrences)
            )
        }   // group and sum together occurrences
            .groupBy { (pair, _) -> pair }
            .mapValues { it.value.sumOf { (_, occurrences) -> occurrences } }
            .toList()

        expandPolymerPairs(expandedPolymerPairs, pairInsertionRules, numberOfSteps - 1)
    }

private fun String.parsePairInsertionRule(): PairInsertionRuleEntry {
    val splitted = this.split(" -> ")
    return splitted[0] to splitted[1].first()
}