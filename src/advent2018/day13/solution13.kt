package advent2018.day13

import utils.Coordinates
import utils.directions
import utils.turnInDirection
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val carts = input.mapIndexed { rowIdx, row ->
            row.mapIndexedNotNull { columnIdx, char ->
                val cart = when (char) {
                    '<' -> 3
                    '^' -> 2
                    '>' -> 1
                    'v' -> 0
                    else -> null
                }
                if (cart == null) null else Triple(Coordinates(columnIdx, rowIdx), cart, 0)
            }
        }
            .filter { it.isNotEmpty() }.flatten().toMutableList()

        var part1 = Coordinates(0, 0)
        while (carts.size > 1) {
            val crashed = mutableSetOf<Int>()
            (0 until carts.size).forEach { currentIdx ->
                carts[currentIdx] = makeMove(carts[currentIdx], input)
                val nodeCars =
                    carts.mapIndexedNotNull { idx, triple -> if (triple.first == carts[currentIdx].first) idx else null }
                if (nodeCars.size > 1) {
                    crashed.addAll(nodeCars)
                    if (part1 == Coordinates(0, 0)) part1 = carts[nodeCars.first()].first
                }
            }
            crashed.sortedDescending().forEach { carts.removeAt(it) }
        }

        val part2 = carts.first().first

        println("Part 1: ${part1.column},${part1.row}\nPart 2: ${part2.column},${part2.row}")
    }
    println("Execution Time = $executionTime ms")
}

private fun makeMove(cart: Triple<Coordinates, Int, Int>, grid: List<String>) =
    when (grid[cart.first.row][cart.first.column]) {
        '+' -> {
            val intersect = listOf("right", "straight", "left")
            val newDir = turnInDirection(cart.second, intersect[cart.third])
            Triple(cart.first + directions[newDir], newDir, (cart.third + 1) % 3)
        }
        '\\' -> {
            val newDir = turnInDirection(
                cart.second, when (cart.second) {
                    1, 3 -> "left"
                    else -> "right"
                }
            )
            Triple(cart.first + directions[newDir], newDir, cart.third)
        }
        '/' -> {
            val newDir = turnInDirection(
                cart.second, when (cart.second) {
                    0, 2 -> "left"
                    else -> "right"
                }
            )
            Triple(cart.first + directions[newDir], newDir, cart.third)
        }
        else -> Triple(cart.first + directions[turnInDirection(cart.second, "straight")], cart.second, cart.third)

    }