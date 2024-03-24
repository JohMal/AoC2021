import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task14Tests {
    private val testPolymerInput = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent()

    @Test
    fun calcExpandedPolymerScore_10() {
        val expandedPolymerScore = calcExpandedPolymerScore(testPolymerInput.split("\n"), 10)
        assertEquals(1588L, expandedPolymerScore)
    }

    @Test
    fun calcExpandedPolymerScore_40() {
        val expandedPolymerScore = calcExpandedPolymerScore(testPolymerInput.split("\n"), 40)
        assertEquals(2188189693529L, expandedPolymerScore)
    }
}