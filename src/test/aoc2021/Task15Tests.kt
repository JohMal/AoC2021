import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task15Tests {
    private val testRiskMapInput = """
       1163751742
       1381373672
       2136511328
       3694931569
       7463417111
       1319128137
       1359912421
       3125421639
       1293138521
       2311944581
    """.trimIndent()

    @Test
    fun calcLowestRiskPath() {
        val totalRisk = calcLowestRiskPath(testRiskMapInput.split("\n"))
        assertEquals(40, totalRisk)
    }

    @Test
    fun calcLowestRiskPathExtended() {
        val totalRisk = calcLowestRiskPathExtended(testRiskMapInput.split("\n"))
        assertEquals(315, totalRisk)
    }
}