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

        val squares = mutableMapOf<Pair<Pair<Int, Int>, Int>, Int>()
        (299 downTo 0).forEach { y ->
            (299 downTo 0).forEach { x ->
                val maxSide = minOf(300 - x, 300 - y)
                (1..maxSide).forEach { size ->
                    squares[Pair(Pair(x + 1, y + 1), size)] =
                            gridPowers[y][x] +
                            (y + 1 until y + size).sumBy { gridPowers[it][x] } +
                            (x + 1 until x + size).sumBy { gridPowers[y][it] } +
                            squares.getOrDefault(Pair(Pair(x + 2, y + 2), size - 1), 0)
                }
            }
        }

        val part1 = squares.filterKeys { it.second == 3 }.maxBy { it.value }!!.key.first.toList().joinToString(",")

        val maxPart2 = squares.maxBy { it.value }!!.key
        val part2 = (maxPart2.first.toList() + listOf(maxPart2.second)).joinToString(",")

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}