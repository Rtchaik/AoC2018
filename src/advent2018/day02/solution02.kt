package advent2018.day02

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val duplicates = input.map { row ->
            val groupedRow = row.groupBy { it }.values.map { it.size }
            (if (groupedRow.contains(2)) 1 else 0) to
                    (if (groupedRow.contains(3)) 1 else 0)
        }
        val part1 = duplicates.sumBy { it.first } * duplicates.sumBy { it.second }

        var part2 = ""
        loop@ for (i in 0..input.size - 2) {
            for (j in i + 1 until input.size) {
                val indexes = input[i].zip(input[j])
                    .mapIndexedNotNull { idx, pair -> if (pair.first != pair.second) idx else null }
                if (indexes.size == 1) {
                    part2 = input[i].removeRange(indexes[0], indexes[0] + 1)
                    break@loop
                }
            }
        }

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}