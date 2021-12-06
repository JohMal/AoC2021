import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task3Tests {
    private val testDiagnosticReport = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010",
    )

    @Test
    fun calculateDiagnosticResultPowerConsumptionTest() {
        with(calculateDiagnosticResult(testDiagnosticReport)) {
            assertEquals(22, gammaRate)
            assertEquals(9, epsilonRate)
            assertEquals(198, powerConsumption)
        }
    }

    @Test
    fun calculateDiagnosticResultLifeSupportRatingTest() {
        with(calculateDiagnosticResult(testDiagnosticReport)) {
            assertEquals(23, oxygenGeneratorRating)
            assertEquals(10, co2ScrubberRating)
            assertEquals(230, lifeSupportRating)
        }
    }
}