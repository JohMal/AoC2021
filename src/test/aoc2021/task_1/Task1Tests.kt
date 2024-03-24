package task_1

import calculateSonarIncreasingDepth
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task1Tests {
    private val exampleSonarDepths = sequenceOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    @Test
    fun calculateSonarIncreasingDepthTest() {
        assertEquals(7, calculateSonarIncreasingDepth(exampleSonarDepths))
    }

    @Test
    fun calculateSonarIncreasingDepthSumTest() {
        assertEquals(5, calculateSonarIncreasingDepth(exampleSonarDepths, 3))
    }
}