import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task6Tests {
    private val testInitialFishState = "3,4,3,1,2"

    @Test
    fun calculateNumberOfLanternFishTest_1days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 1)
        assertEquals(5, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_2days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 2)
        assertEquals(6, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_3days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 3)
        assertEquals(7, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_4days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 4)
        assertEquals(9, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_5days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 5)
        assertEquals(10, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_18days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 18)
        assertEquals(26, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_80days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 80)
        assertEquals(5934, numberOfLanternFish)
    }

    @Test
    fun calculateNumberOfLanternFishTest_256days() {
        val numberOfLanternFish = calculateNumberOfLanternFish(testInitialFishState, 256)
        assertEquals(26984457539, numberOfLanternFish)
    }
}