package utils

import kotlin.math.abs

//direction 0 = North
val directions = listOf(Coordinates(0, 1), Coordinates(1, 0), Coordinates(0, -1), Coordinates(-1, 0))
val diagonals = listOf(Coordinates(-1, -1), Coordinates(-1, 1), Coordinates(1, 1), Coordinates(1, -1))


data class Coordinates(val column: Int, val row: Int) {

    operator fun plus(direction: Coordinates) =
        Coordinates(column + direction.column, row + direction.row)

    operator fun minus(direction: Coordinates) =
        Coordinates(column - direction.column, row - direction.row)
}

fun turnInDirection(direction: Int, action: String) = when (action) {
    "straight" -> direction
    "right" -> (direction + 1) % 4
    "left" -> if (direction == 0) 3 else direction - 1
    "back" -> (direction + 2) % 4
    else -> throw IllegalArgumentException("Unrecognized action: $action")
}

fun Coordinates.distance(target: Coordinates) = abs(this.row - target.row) + abs(this.column - target.column)