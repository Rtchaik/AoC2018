package advent2018.day06

import utils.Coordinates
import utils.distance
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val coordinates = input.map { it.split(", ") }.map { Coordinates(it[0].toInt(), it[1].toInt()) }
        val maxRow = coordinates.maxBy { it.row }!!.row
        val maxColumn = coordinates.maxBy { it.column }!!.column
        val infinities = mutableSetOf<Int>()
        val part1 = (0..coordinates.size).map { 0 }.toMutableList()
        var part2 = 0

        (0..maxRow).forEach { row ->
            (0..maxColumn).forEach { column ->
                val distances = coordinates.map { it.distance(Coordinates(column, row)) }
                val minDistance = distances.min()
                if (distances.filter { it == minDistance }.size == 1) {
                    val idxMinDistance = distances.indexOf(minDistance)
                    part1[idxMinDistance]++
                    if (row == 0 || row == maxRow || column == 0 || column == maxColumn) infinities.add(idxMinDistance)
                }
                if (distances.sum() < maxDistance) part2++
            }
        }
        infinities.sortedDescending().forEach { part1.removeAt(it) }
        println("Part 1: ${part1.max()}\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}