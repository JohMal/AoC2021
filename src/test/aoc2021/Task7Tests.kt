import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task7Tests {
    private val testCrapPositions = "16,1,2,0,4,2,7,1,2,14"

    @Test
    fun calculateMinCrapAlignmentFuelCosts_Constant_Test() {
        val crapAlignmentFuelCosts = calculateMinCrapAlignmentFuelCosts(testCrapPositions, FuelCostStrategy.CONSTANT)
        assertEquals(37, crapAlignmentFuelCosts)
    }

    @Test
    fun calculateMinCrapAlignmentFuelCosts_Inc_Test() {
        val crapAlignmentFuelCosts = calculateMinCrapAlignmentFuelCosts(testCrapPositions, FuelCostStrategy.INC)
        assertEquals(168, crapAlignmentFuelCosts)
    }
}