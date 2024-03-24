import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task9Tests {
    private val testHeightMap = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent()

    @Test
    fun findTotalRiskLevelOfHeightMap() {
        val riskLevel = findTotalRiskLevelOfHeightMap(testHeightMap.split("\n"))

        assertEquals(15, riskLevel)
    }
}