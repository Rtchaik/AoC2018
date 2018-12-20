package advent2018.day20

import utils.Coordinates
import utils.directions
import kotlin.system.measureTimeMillis

val mGrid = (0..2000).map { (0..2000).map { '#' }.toMutableList() }.toMutableList()

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val startPos = Coordinates(1000, 1000)
        mGrid[startPos.row][startPos.column] = 'X'
        runInstructions(listOf(startPos))
        val doors = largestDoors(listOf(startPos))
        val part1 = doors.first / 2
        val part2 = doors.second
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private tailrec fun largestDoors(
    positions: List<Coordinates>,
    visited: List<Coordinates> = listOf(), count: Int = 0, part2: Int = 0
): Pair<Int, Int> = if (positions.isEmpty()) Pair(count, part2) else {
    val newSpots = positions.flatMap { current ->
        directions.map { it + current }.filter { mGrid[it.row][it.column] != '#' && !visited.contains(it) }
    }.distinct()
    largestDoors(newSpots, visited + newSpots, count.inc(),
        part2 + if (count >= 1000 * 2 - 1) newSpots.count { mGrid[it.row][it.column] == '.' } else 0)
}

private tailrec fun runInstructions(
    position: List<Coordinates>, nodes: MutableList<Pair<Int, List<Coordinates>>> = mutableListOf(),
    level: Int = 0, idx: Int = 1
) {
    if (input[idx] != '$') {
        when (input[idx]) {
            '(' -> {
                nodes.add(level to position)
                runInstructions(position, nodes, level.inc(), idx.inc())
            }
            '|' -> {
                nodes.add(level to position)
                runInstructions(nodes.findLast { it.first == level - 1 }!!.second, nodes, level, idx.inc())
            }
            ')' -> {
                if (input[idx - 1] != ')') nodes.add(level to position)
                nodes.removeAt(nodes.indexOfLast { it.first == level - 1 })
                nodes.addAll(nodes.filter { it.first == level }.map { it.first - 1 to it.second })
                nodes.removeIf { it.first == level }
                runInstructions(nodes.findLast { it.first == level - 1 }!!.second, nodes, level.dec(), idx.inc())
            }
            else -> runInstructions(position.map { buildMap(it, input[idx]) }, nodes, level, idx.inc())
        }
    }
}

private fun buildMap(position: Coordinates, instruction: Char): Coordinates {
    val idx = "NESW".indexOf(instruction)
    var newPos = position + directions[idx]
    mGrid[newPos.row][newPos.column] = if (instruction in "NS") '-' else '|'
    newPos += directions[idx]
    mGrid[newPos.row][newPos.column] = '.'
    return newPos
}