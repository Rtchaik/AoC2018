package advent2018.day17

import utils.Coordinates
import kotlin.system.measureTimeMillis

var grid = listOf(charArrayOf())

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val parsedInput = input.map { line -> line.split("""=|, |\.\.""".toRegex()) }
            .flatMap { (it[3].toInt()..it[4].toInt()).map { y -> if (it[0] == "x") it[1].toInt() to y else y to it[1].toInt() } }
            .distinct()
        val minX = parsedInput.minBy { it.first }!!.first - 2
        val minY = parsedInput.minBy { it.second }!!.second - 1
        val maxX = parsedInput.maxBy { it.first }!!.first + 2
        val maxY = parsedInput.maxBy { it.second }!!.second
        grid = (minY..maxY).map { (minX..maxX).map { '.' }.toCharArray() }
        parsedInput.forEach { grid[it.second - minY][it.first - minX] = '#' }
        grid[0][500 - minX] = '+'
        var current = listOf(Coordinates(500 - minX, 0))
        while (current.isNotEmpty()) {
            current = current.fold(listOf<Coordinates>()) { acc, coord -> acc + waterFlow(coord) }
                .filter { it.row < grid.size - 1 }.distinct()
        }
//        for (item in grid) println(item.joinToString(""))//SHOW PICTURE
        val part1 = grid.map { row -> row.count { it == '~' || it == '|' } }.sum()
        val part2 = grid.map { row -> row.count { it == '~' } }.sum()
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun waterFlow(current: Coordinates): List<Coordinates> {
    val newSpots = mutableListOf<Coordinates>()
    when {
        grid[current.row + 1][current.column] == '.' -> {
            grid[current.row + 1][current.column] = '|'
            newSpots.add(Coordinates(current.column, current.row + 1))
        }
        grid[current.row][current.column] == '|' && grid[current.row][current.column - 1] == '#' && grid[current.row][current.column + 1] == '#' -> {
            grid[current.row][current.column] = '~'
            newSpots.add(Coordinates(current.column, current.row - 1))
        }
        grid[current.row + 1][current.column] == '|' || grid[current.row - 1][current.column] == '|' && grid[current.row][current.column] == '|' && (grid[current.row][current.column - 1] == '|' || grid[current.row][current.column - 1] == '#') && (grid[current.row][current.column + 1] == '|' || grid[current.row][current.column + 1] == '#') -> {
            newSpots.add(Coordinates(current.column, grid.size - 1))
        }
        else -> {
            var water = '|'
            var startIdx = grid[current.row].sliceArray(0 until current.column).indexOfLast { it == '#' } + 1
            val end = grid[current.row].drop(current.column).indexOfFirst { it == '#' }
            var endIdx = if (end == -1) grid[0].size else end + current.column - 1
            val startIdxNext =
                grid[current.row + 1].sliceArray(0 until current.column).indexOfLast { it == '.' || it == '|' }
            val endIdxNext =
                grid[current.row + 1].drop(current.column).indexOfFirst { it == '.' || it == '|' } + current.column
            when {
                endIdx > endIdxNext || startIdx < startIdxNext -> {
                    if (startIdx < startIdxNext) {
                        startIdx = startIdxNext
                        newSpots.add(
                            if (grid[current.row][startIdx] == '|') Coordinates(current.column, grid.size - 1)
                            else Coordinates(startIdx, current.row)
                        )
                    }
                    if (endIdx > endIdxNext) {
                        endIdx = endIdxNext
                        newSpots.add(
                            if (grid[current.row][endIdx] == '|') Coordinates(current.column, grid.size - 1)
                            else Coordinates(endIdx, current.row)
                        )
                    }
                }
                else -> {
                    water = '~'
                    newSpots.add(Coordinates(current.column, current.row - 1))
                }
            }
            (startIdx..endIdx).forEach { grid[current.row][it] = water }
        }
    }
    return newSpots
}