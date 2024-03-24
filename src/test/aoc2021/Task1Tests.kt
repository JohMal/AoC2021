import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task1Tests {
    private val testSonarDepths = sequenceOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    @Test
    fun calculateSonarIncreasingDepthTest() {
        assertEquals(7, calculateSonarIncreasingDepth(testSonarDepths))
    }

    @Test
    fun calculateSonarIncreasingDepthSumTest() {
        assertEquals(5, calculateSonarIncreasingDepth(testSonarDepths, 3))
    }
}