package advent2018.day05

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val part1 = reactedLength(input)

        val unitTypes = input.groupBy { it.toLowerCase() }.keys
        val part2 = unitTypes.map {
            reactedLength(input.fold("") { acc, char ->
                if (it.equals(char, true)) acc else acc + char
            })
        }.min()

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private tailrec fun reactedLength(polymer: String, reacted: String = "", idx: Int = 0): Int =
    if (idx >= polymer.length) reacted.length else {
        if (reacted.isNotEmpty() && polymer[idx].equals(reacted.last(), true) && polymer[idx] != reacted.last())
            reactedLength(polymer, reacted.dropLast(1), idx.inc()) else reactedLength(
            polymer, reacted + polymer[idx], idx.inc()
        )
    }