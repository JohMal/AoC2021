import java.io.File

private const val INPUT_FILE_PATH = "src/main/resources/input_task_2.txt"

enum class MoveDirection(val value: String) {
    FORWARD("forward"),
    DOWN("down"),
    UP("up");

    companion object {
        fun fromValue(value: String): MoveDirection? = values().find { it.value == value }
    }
}

data class MoveCommand(
    val direction: MoveDirection,
    val distance: Int
)

data class Position(
    val depth: Int = 0,
    val horizPos: Int = 0,
    val aim: Int = 0
)


fun main() {
    // Task 2.1
    File(INPUT_FILE_PATH)
        .useLines { lines -> calculatePosition(lines) }
        .also { println("Task 2.1: ${it.depth * it.horizPos}") }

    // Task 2.2
    File(INPUT_FILE_PATH)
        .useLines { lines -> calculatePositionWithAim(lines) }
        .also { println("Task 2.1: ${it.depth * it.horizPos}") }
}

fun calculatePosition(moveCommands: Sequence<String>) =
    calculatePositionIntern(moveCommands.mapNotNull { it.parseMoveCommand() })

fun calculatePositionWithAim(moveCommands: Sequence<String>) =
    calculatePositionWithAimIntern(moveCommands.mapNotNull { it.parseMoveCommand() })

private fun String.parseMoveCommand(): MoveCommand? {
    val splitted = this.split(" ")
    return MoveCommand(
        direction = MoveDirection.fromValue(splitted[0]) ?: return null,
        distance = splitted[1].toInt()
    )
}

private fun calculatePositionIntern(moveCommands: Sequence<MoveCommand>): Position {
    return moveCommands.fold(Position()) { acc, e ->
        when (e.direction) {
            MoveDirection.FORWARD -> acc.copy(horizPos = acc.horizPos + e.distance)
            MoveDirection.DOWN -> acc.copy(depth = acc.depth + e.distance)
            MoveDirection.UP -> acc.copy(depth = acc.depth - e.distance)
        }
    }
}

private fun calculatePositionWithAimIntern(moveCommands: Sequence<MoveCommand>): Position {
    return moveCommands.fold(Position()) { acc, e ->
        when (e.direction) {
            MoveDirection.FORWARD -> acc.copy(
                horizPos = acc.horizPos + e.distance,
                depth = acc.depth + acc.aim * e.distance
            )
            MoveDirection.DOWN -> acc.copy(aim = acc.aim + e.distance)
            MoveDirection.UP -> acc.copy(aim = acc.aim - e.distance)
        }
    }
}