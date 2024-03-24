import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_3.txt"

data class DiagnosticResult(
    val gammaRate: Int = 0,
    val epsilonRate: Int = 0,
    val oxygenGeneratorRating: Int = 0,
    val co2ScrubberRating: Int = 0
) {
    val powerConsumption: Int
        get() = gammaRate * epsilonRate

    val lifeSupportRating: Int
        get() = oxygenGeneratorRating * co2ScrubberRating
}

fun main() {
    val diagnosticReportLines = File(INPUT_FILE_PATH).readLines()

    calculateDiagnosticResult(diagnosticReportLines).also {
        println("Task 3.1: ${it.powerConsumption}")
        println("Task 3.2: ${it.lifeSupportRating}")
    }
}

fun calculateDiagnosticResult(diagnosticReport: List<String>): DiagnosticResult {
    if (diagnosticReport.isEmpty()) return DiagnosticResult()

    val diagnosticAnalysis = diagnosticReport.calcDiagnosisAnalysis()

    val gammaRate = diagnosticAnalysis
        .map { if (it >= 0) 1 else 0 }
        .joinToString(separator = "")
        .binaryStringToInteger()

    val epsilonRate = diagnosticAnalysis
        .map { if (it >= 0) 0 else 1 }
        .joinToString(separator = "")
        .binaryStringToInteger()

    val oxygenGeneratorRating = filterDiagnosisLinesWithCriteria(
        diagnosisLines = diagnosticReport,
        criteria = { diagAnaBit, digit ->
            digit == if (diagAnaBit >= 0) 1 else 0
        }
    )

    val co2ScrubberRating = filterDiagnosisLinesWithCriteria(
        diagnosisLines = diagnosticReport,
        criteria = { diagAnaBit, digit ->
            digit == if (diagAnaBit >= 0) 0 else 1
        }
    )

    return DiagnosticResult(gammaRate, epsilonRate, oxygenGeneratorRating, co2ScrubberRating)
}

private fun List<Int>.addDiagnosticLine(diagnosticLine: String): List<Int> {
    return this.zip(diagnosticLine.toList()) { charCounter, lineChar ->
        charCounter + if (lineChar == '1') 1 else -1
    }
}

private fun String.binaryStringToInteger(): Int {
    return Integer.parseInt(this, 2)
}

private fun List<String>.calcDiagnosisAnalysis(): List<Int> =
    fold(List(first().length) { 0 }) { acc, e -> acc.addDiagnosticLine(e) }


private tailrec fun filterDiagnosisLinesWithCriteria(
    criteria: (Int, Int) -> Boolean,
    diagnosisLines: List<String>,
    currentBit: Int = 0,
): Int {
    assert(diagnosisLines.isNotEmpty()) { "Illegal Input. There is no line matching criteria at bit $currentBit" }
    return when (diagnosisLines.size) {
        1 -> return diagnosisLines.first().binaryStringToInteger()
        else -> {
            val diagnosticAnalysis = diagnosisLines.calcDiagnosisAnalysis()
            filterDiagnosisLinesWithCriteria(
                criteria,
                diagnosisLines.filter {
                    it.getOrNull(currentBit)
                        ?.let { digit -> criteria(diagnosticAnalysis[currentBit], Character.getNumericValue(digit)) }
                        ?: false
                },
                currentBit + 1,
            )
        }
    }
}