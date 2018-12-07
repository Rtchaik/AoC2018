package advent2018.day01

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val changes = input.map { it.toInt() }

        val part1 = changes.sum()
        val part2 = findDuplicate(changes)

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun findDuplicate(changes: List<Int>): Int {
    var sum = 0
    val frequencies = mutableListOf<Int>()
    var idx = 0
    while (!frequencies.contains(sum)) {
        frequencies.add(sum)
        sum += changes[idx % changes.size]
        idx++
    }
    return sum
}