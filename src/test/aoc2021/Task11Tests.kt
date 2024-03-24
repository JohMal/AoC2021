import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task11Tests {
    private val testOctopusEnergyMap = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent()

    @Test
    fun calcNumberOfFlashesAfter10Days() {
        val numberOfFlashes = calcNumberOfFlashes(testOctopusEnergyMap.split("\n"), 10)
        assertEquals(204, numberOfFlashes)
    }

    @Test
    fun calcNumberOfFlashesAfter100Days() {
        val numberOfFlashes = calcNumberOfFlashes(testOctopusEnergyMap.split("\n"), 100)
        assertEquals(1656, numberOfFlashes)
    }

    @Test
    fun calcAllFlashDay() {
        val allFlashDay = calcAllFlashDay(testOctopusEnergyMap.split("\n"))
        assertEquals(195, allFlashDay)
    }
}