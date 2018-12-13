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
            var currentIdx = 0
            while (currentIdx < carts.size) {
                carts[currentIdx] =
                        makeMove(carts[currentIdx], input[carts[currentIdx].first.row][carts[currentIdx].first.column])
                val nodeCars =
                    carts.mapIndexedNotNull { idx, triple -> if (triple.first == carts[currentIdx].first) idx else null }
                if (nodeCars.size > 1) {
                    if (part1 == Coordinates(0, 0)) part1 = carts[nodeCars.first()].first
                    if (currentIdx == nodeCars.max()) currentIdx--
                    nodeCars.sortedDescending().forEach { carts.removeAt(it) }
                } else currentIdx++
            }
            carts.sortWith(compareBy({ it.first.row }, { it.first.column }))
        }

        val part2 = carts.first().first

        println("Part 1: ${part1.column},${part1.row}\nPart 2: ${part2.column},${part2.row}")
    }
    println("Execution Time = $executionTime ms")
}

private fun makeMove(cart: Triple<Coordinates, Int, Int>, road: Char): Triple<Coordinates, Int, Int> {
    val newDir = turnInDirection(
        cart.second, when (road) {
            '+' -> "RSL"[cart.third]
            '\\' -> when (cart.second) {
                1, 3 -> 'L'
                else -> 'R'
            }
            '/' -> when (cart.second) {
                0, 2 -> 'L'
                else -> 'R'
            }
            else -> 'S'
        }
    )
    return Triple(cart.first + directions[newDir], newDir, if (road == '+') (cart.third + 1) % 3 else cart.third)
}