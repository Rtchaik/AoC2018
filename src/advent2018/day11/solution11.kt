package advent2018.day11

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val gridPowers = (0 until 300).map { y ->
            (0 until 300).map { x ->
                val rackId = (x + 1) + 10
                ((rackId * (y + 1) + serialNumber) * rackId / 100).toString().takeLast(1).toInt() - 5
            }
        }

        val squares = mutableMapOf<Triple<Int, Int, Int>, Int>()
        (299 downTo 0).forEach { y ->
            (299 downTo 0).forEach { x ->
                val maxSide = minOf(300 - x, 300 - y)
                var sidesSum = 0
                (1..maxSide).forEach { size ->
                    sidesSum += gridPowers[y + size - 1][x] + gridPowers[y][x + size - 1]
                    squares[Triple(x + 1, y + 1, size)] = sidesSum +
                            squares.getOrDefault(Triple(x + 2, y + 2, size - 1), 0)
                }
            }
        }

        val part1 = squares.filterKeys { it.third == 3 }.maxBy { it.value }!!.key.toList().dropLast(1).joinToString(",")
        val part2 = squares.maxBy { it.value }!!.key.toList().joinToString(",")
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}