package advent2018.day18

import utils.Coordinates
import utils.diagonals
import utils.directions
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        var part1 = input
        repeat(10) { part1 = passMinute(part1) }

        val allMaps = mutableListOf<List<String>>()
        var currentMap = input
        while (!allMaps.contains(currentMap)) {
            allMaps.add(currentMap)
            currentMap = passMinute(currentMap)
        }
        val cycle = allMaps.drop(allMaps.indexOf(currentMap))
        val part2 = cycle[(1000000000 - allMaps.indexOf(currentMap)) % cycle.size]

        println("Part 1: ${calculateResources(part1)}\nPart 2: ${calculateResources(part2)}")
    }
    println("Execution Time = $executionTime ms")
}

private fun calculateResources(currentMap: List<String>) =
    currentMap.sumBy { row -> row.count { it == '|' } } * currentMap.sumBy { row -> row.count { it == '#' } }

private fun passMinute(lumberArea: List<String>): List<String> {
    val newArea = (0 until lumberArea.size).map { (0 until lumberArea[0].length).map { ' ' }.toMutableList() }
    lumberArea.forEachIndexed { rowIdx, row ->
        row.forEachIndexed { columnIdx, char ->
            val adjacent = (directions + diagonals).map { it + Coordinates(columnIdx, rowIdx) }
                .filter { it.row in 0 until lumberArea.size && it.column in 0 until row.length }
                .map { lumberArea[it.row][it.column] }
            newArea[rowIdx][columnIdx] = when (char) {
                '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
                '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
                '#' -> if (adjacent.count { it == '#' } >= 1 && adjacent.count { it == '|' } >= 1) '#' else '.'
                else -> throw IllegalArgumentException("Wrong area symbol")
            }
        }
    }
    return newArea.map { it.joinToString("") }
}