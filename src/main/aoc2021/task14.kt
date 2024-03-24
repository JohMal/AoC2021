import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_14.txt"

private typealias PairInsertionRules = Map<Pair<Char, Char>, Char>
private typealias PairInsertionRuleEntry = Pair<Pair<Char, Char>, Char>

fun main() {
    val polymerInput = File(INPUT_FILE_PATH).readLines()

    // Task 14.1
    calcExpandedPolymerScore(polymerInput, 10)
        .also { println("Task 14.1: $it") }

    // Task 14.2

}

fun calcExpandedPolymerScore(polymerInput: List<String>, numberOfSteps: Int): Long {
    val polymerTemplate = polymerInput.first()
    val pairInsertionRules = polymerInput.drop(2).associate { rule ->
        rule.parsePairInsertionRule().let { it.first to it.second }
    }

    val expandedPolymer = expandPolymer(polymerTemplate, pairInsertionRules, numberOfSteps)
    val characterGroups = expandedPolymer.groupBy { it }.mapValues { it.value.count().toLong() }
    val minCharacterCount = characterGroups.minOf { it.value }
    val maxCharacterCount = characterGroups.maxOf { it.value }

    return maxCharacterCount - minCharacterCount
}

private tailrec fun expandPolymer(polymer: String, pairInsertionRules: PairInsertionRules, numberOfSteps: Int): String =
    if (numberOfSteps == 0) polymer
    else {
        val expandedPolymer = polymer.windowed(2).fold("${polymer[0]}") { acc, e ->
            val insertChar = pairInsertionRules[Pair(e[0], e[1])] ?: ""
            "$acc$insertChar${e[1]}"
        }
        expandPolymer(expandedPolymer, pairInsertionRules, numberOfSteps - 1)
    }

private fun String.parsePairInsertionRule(): PairInsertionRuleEntry {
    val splitted = this.split(" -> ")
    val ruleCharacterPair = splitted[0].toCharArray()
    val ruleInsertCharacter = splitted[1].first() // first and only character
    return Pair(ruleCharacterPair[0], ruleCharacterPair[1]) to ruleInsertCharacter
}