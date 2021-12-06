import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Task2Tests {
    private val testCommands = sequenceOf(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2",
    )

    @Test
    fun calculatePositionTest() {
        val pos = calculatePosition(testCommands.mapNotNull { it.toMoveCommand() })
        assertEquals(Position(depth = 10, horizPos = 15), pos)
    }

    @Test
    fun calculatePositionWithAimTest() {
        val pos = calculatePositionWithAim(testCommands.mapNotNull { it.toMoveCommand() })
        assertEquals(60, pos.depth)
        assertEquals(15, pos.horizPos)
    }
}