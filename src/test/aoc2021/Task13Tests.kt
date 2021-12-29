import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task13Tests {
    private val testDottedPaperInput = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent()

    @Test
    fun calcNumberOfVisibleDotsFold1() {
        val numberOfVisibleDots = calcNumberOfVisibleDots(testDottedPaperInput.split("\n"), 1)
        assertEquals(17, numberOfVisibleDots)
    }

    @Test
    fun calcNumberOfVisibleDotsFold2() {
        val numberOfVisibleDots = calcNumberOfVisibleDots(testDottedPaperInput.split("\n"), 2)
        assertEquals(16, numberOfVisibleDots)
    }

    @Test
    fun calcNumberOfVisibleDotsFoldAll() {
        val numberOfVisibleDots = calcNumberOfVisibleDots(testDottedPaperInput.split("\n"))
        assertEquals(16, numberOfVisibleDots)
    }
}