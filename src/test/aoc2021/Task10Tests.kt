import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task10Tests {
    private val testNavigationSubsystemCode = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent()

    @Test
    fun calcSyntaxErrorScore() {
        val score = calcSyntaxErrorScore(testNavigationSubsystemCode.split("\n"))
        assertEquals(26397, score)
    }

    @Test
    fun calcAutocompleteScore() {
        val score = calcAutocompleteScore(testNavigationSubsystemCode.split("\n"))
        assertEquals(288957, score)
    }
}