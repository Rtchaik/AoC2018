package advent2018.day13

import utils.Coordinates
import utils.directions
import utils.turnInDirection
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val carts = input.mapIndexed { row, s ->
            s.mapIndexedNotNull { column, c ->
                val cart = findCarts(c)
                if (cart == null) null else Triple(Coordinates(column, row), cart, 0)
            }
        }
            .filter { it.isNotEmpty() }.flatten().toMutableList()

        var part1 = Coordinates(0, 0)
        while (carts.size > 1) {
            val crashed = mutableSetOf<Int>()
            (0 until carts.size).forEach { current ->
                carts[current] = makeMove(carts[current], input)
                val node =
                    carts.mapIndexedNotNull { idx, triple -> if (triple.first == carts[current].first) idx else null }
                if (node.size > 1) {
                    crashed.addAll(node)
                    if (part1 == Coordinates(0, 0))  part1 = carts[node[0]].first

                }
            }
            crashed.sortedDescending().forEach { carts.removeAt(it) }
        }

        val part2 = carts[0].first

        println("Part 1: ${part1.column},${part1.row}\nPart 2: ${part2.column},${part2.row}")
    }
    println("Execution Time = $executionTime ms")
}

private fun findCarts(char: Char) = when (char) {
    'v' -> 0
    '<' -> 3
    '^' -> 2
    '>' -> 1
    else -> null
}

private fun makeMove(cart: Triple<Coordinates, Int, Int>, grid: List<String>) =
    when (grid[cart.first.row][cart.first.column]) {
        '+' -> {
            val intersect = listOf("right", "straight", "left")
            val newDir = turnInDirection(cart.second, intersect[cart.third])
            Triple(cart.first + directions[newDir], newDir, (cart.third + 1) % 3)
        }
        '\\' -> {
            var turn = ""
            when (cart.second) {
                1, 3 -> turn = "left"
                0, 2 -> turn = "right"
            }
            val newDir = turnInDirection(cart.second, turn)
            Triple(cart.first + directions[newDir], newDir, cart.third)
        }
        '/' -> {
            var turn = ""
            when (cart.second) {
                0, 2 -> turn = "left"
                1, 3 -> turn = "right"
            }
            val newDir = turnInDirection(cart.second, turn)
            Triple(cart.first + directions[newDir], newDir, cart.third)
        }
        else -> Triple(cart.first + directions[turnInDirection(cart.second, "straight")], cart.second, cart.third)

    }