package advent2018.day03

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val claims = input.map { item -> item.split("""\D""".toRegex()).filter { it.isNotBlank() }.map { it.toInt() } }
        val fabric = (0..999).map { (0..999).map { 0 }.toMutableList() }

        claims.forEach { item ->
            (item[2] until item[2] + item[4]).forEach { row ->
                (item[1] until item[1] + item[3])
                    .forEach { column -> fabric[row][column] += 1 }
            }
        }
        val part1 = fabric.sumBy { row -> row.count { it > 1 } }

        var part2 = 0
        for (item in claims) {
            val sum = fabric.subList(item[2], item[2] + item[4]).sumBy { it.subList(item[1], item[1] + item[3]).sum() }
            if (sum == item[3] * item[4]) {
                part2 = item[0]
                break
            }
        }

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}