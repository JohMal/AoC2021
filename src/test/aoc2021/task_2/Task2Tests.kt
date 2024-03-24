package task_2

import Position
import calculatePosition
import calculatePositionWithAim
import calculateSonarIncreasingDepth
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import toMoveCommand

internal class Task2Tests {
    private val exampleCommands = listOf(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2",
    )

    @Test
    fun calculatePositionTest() {
        val pos = calculatePosition(exampleCommands.mapNotNull { it.toMoveCommand() })
        assertEquals(Position(depth = 10, horizPos = 15), pos)
    }

    @Test
    fun calculatePositionWithAimTest() {
        val pos = calculatePositionWithAim(exampleCommands.mapNotNull { it.toMoveCommand() })
        assertEquals(60, pos.depth)
        assertEquals(15, pos.horizPos)
    }
}