import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_8.txt"

data class SegmentInputLine(
    val digitScan: List<BooleanArray>,
    val digitOutput: List<BooleanArray>
)

fun main() {

    // Task 8.1
    File(INPUT_FILE_PATH)
        .useLines { getNumberOfUniqueSegmentNumbers(it) }
        .also { println("Task 8.1: $it") }

    // Task 8.2
    File(INPUT_FILE_PATH)
        .useLines { getDigitOutputSum(it) }
        .also { println("Task 8.2: $it") }
}

fun getNumberOfUniqueSegmentNumbers(segmentInput: Sequence<String>): Int =
    getNumberOfUniqueSegmentNumbersIntern(segmentInput.map { it.parseSegmentInputLine() })

fun getDigitOutputSum(segmentInput: Sequence<String>): Int = segmentInput
    .map { it.parseSegmentInputLine() }
    .sumOf { getDigitOutput(it) }

private fun getNumberOfUniqueSegmentNumbersIntern(segmentLines: Sequence<SegmentInputLine>): Int =
    segmentLines.fold(0) { acc, e -> acc + countNumberOfUniqueSegments(e) }

private fun countNumberOfUniqueSegments(segmentLine: SegmentInputLine): Int = segmentLine
    .digitOutput.map { singleDigit -> singleDigit.count { it } } // active segment counts
    .filter { it in listOf(2, 3, 4, 7) }.count() // number of unique segment counts

private fun getDigitOutput(segmentLine: SegmentInputLine): Int {
    val digitMapping = calculateDigitMapping(segmentLine.digitScan)

    return segmentLine.digitOutput
        .map { digit -> digitMapping.indexOfFirst { digit.contentEquals(it) } }
        .joinToString("") { it.toString() }
        .toInt()
}

private fun calculateDigitMapping(digitScanInput: List<BooleanArray>): Array<BooleanArray> {
    val numberOfActiveSegments = digitScanInput.map { digit -> digit.filter { it }.count() }

    //map unique segments
    val digit1 = digitScanInput[numberOfActiveSegments.indexOfFirst { it == 2 }]
    val digit4 = digitScanInput[numberOfActiveSegments.indexOfFirst { it == 4 }]
    val digit7 = digitScanInput[numberOfActiveSegments.indexOfFirst { it == 3 }]
    val digit8 = digitScanInput[numberOfActiveSegments.indexOfFirst { it == 7 }]

    // map other segments
    val indices5Segment = numberOfActiveSegments.getIndicesOfNSegmentDigits(5)
    val digits5Segment = digitScanInput.filterIndexed { index, _ -> index in indices5Segment }

    val indices6Segment = numberOfActiveSegments.getIndicesOfNSegmentDigits(6)
    val digits6Segment = digitScanInput.filterIndexed { index, _ -> index in indices6Segment }

    // 6 segments active + inactive segment that is in 1 active => 6
    val digit6 = digits6Segment.first {
        (it zip digit1).any { (first, second) -> !first && second }
    }

    // 5 segments active + inactive segment that is in 6 inactive => 5
    val digit5 = digits5Segment.first {
        (it zip digit6).any { (first, second) -> !first && !second }
    }

    // 5 segments active + inactive segment that is in 1 active + not digit 5 => 2
    val digit2 = (digits5Segment - digit5).first {
        (it zip digit1).any { (first, second) -> !first && second }
    }

    // 5 segments active + not digit 5 and not digit 2 => 3
    val digit3 = (digits5Segment - listOf(digit2, digit5)).first()

    // 6 segments active +  inactive segment that is in 4 inactive + not digit 6 => 9
    val digit9 = (digits6Segment - digit6).first {
        (it zip digit4).any { (first, second) -> !first && !second }
    }

    // 6 segments active + not digit 6 + not digit 9 => 0
    val digit0 = (digits6Segment - listOf(digit6, digit9)).first()

    return arrayOf(digit0, digit1, digit2, digit3, digit4, digit5, digit6, digit7, digit8, digit9)
}

private fun List<Int>.getIndicesOfNSegmentDigits(n: Int) = this
    .mapIndexed { index, i -> index to i }
    .filter { (_, i) -> i == n }
    .map { (index, _) -> index }


//parsing the input
private fun String.parseSegmentInputLine(): SegmentInputLine {
    val splitted = this.split(" | ")
    val digitScan = splitted[0].split(" ").map { it.parseDigitSegment() }
    val digitOutput = splitted[1].split(" ").map { it.parseDigitSegment() }
    return SegmentInputLine(digitScan, digitOutput)
}

private fun String.parseDigitSegment(): BooleanArray =
    ('a'..'g').map { this.contains(it) }.toBooleanArray()