package advent2018.day10

import utils.Coordinates
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val parsedInput = input.map { item ->
            item.split("""\w+=<|>|,""".toRegex())
                .mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null }
        }
            .map { Pair(Coordinates(it[0], it[1]), Coordinates(it[2], it[3])) }.toMutableList()

        var height =
            parsedInput.maxBy { it.first.column }!!.first.column - parsedInput.minBy { it.first.column }!!.first.column
        var part2 = 0
        var counter = 0
        while (part2 == 0) {
            parsedInput.forEachIndexed { idx, point ->
                parsedInput[idx] = Pair(point.first + point.second, point.second)
            }
            val heightNew =
                parsedInput.maxBy { it.first.column }!!.first.column - parsedInput.minBy { it.first.column }!!.first.column
            if (heightNew > height) {
                parsedInput.forEachIndexed { idx, point ->
                    parsedInput[idx] = Pair(point.first - point.second, point.second)
                }
                val minX = parsedInput.minBy { it.first.row }!!.first.row
                val maxX = parsedInput.maxBy { it.first.row }!!.first.row
                val minY = parsedInput.minBy { it.first.column }!!.first.column
                val maxY = parsedInput.maxBy { it.first.column }!!.first.column
                val grid = (minY..maxY).map { (minX..maxX).map { ' ' }.toMutableList() }
                parsedInput.forEach { point -> grid[point.first.column - minY][point.first.row - minX] = '#' }
                for (item in grid) println(item.joinToString(""))
                part2 = counter
            } else {
                counter++
                height = heightNew
            }
        }
        println("\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}