package advent2018.day07

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val parsedInput = input.map { it.split(" ") }.map { it[1] to it[7] }
        var remaining = parsedInput.toMutableList()
        val last = remaining.map { it.second }.distinct() - remaining.map { it.first }.distinct()
        if (last.size > 1) {
            println("The tree has more than one ends. The algorithm need to be updated! ")
        } else {
            var part1 = ""
            while (remaining.size > 0) {
                val available =
                    (remaining.map { it.first }.distinct() - remaining.map { it.second }.distinct()).sorted()
                part1 += available[0]
                remaining.removeAll { it.first == available[0] }
            }
            part1 += last[0]

            remaining = parsedInput.toMutableList()
            val jobs = ('A'..'Z').map { it.toString() }
            val workers = (0 until numWorkers).map { Triple(it, 0, ".") }.toMutableList()
            var part2 = 0
            while (remaining.size > 0 || workers.any { it.second != 0 }) {
                val available =
                    ((remaining.map { it.first }.distinct() - remaining.map { it.second }.distinct() - workers.map { it.third }))
                        .sorted().toMutableList()
                while (available.isNotEmpty() && workers.any { it.second == 0 }) {
                    val idx = workers.indexOfFirst { it.second == 0 }
                    workers[idx] =
                            Triple(workers[idx].first, jobs.indexOf(available[0]) + 1 + stepDuration, available[0])
                    available.removeAt(0)
                }
                val working = workers.filter { it.second != 0 }
                val minWork = working.minBy { it.second }!!
                part2 += minWork.second
                working.filter { it.second == minWork.second }
                    .forEach { finished -> remaining.removeAll { it.first == finished.third } }
                working.forEach { workers[it.first] = Triple(it.first, it.second - minWork.second, it.third) }
            }
            part2 += jobs.indexOf(last[0]) + 1 + stepDuration
            println("Part 1: $part1\nPart 2: $part2")
        }
    }
    println("Execution Time = $executionTime ms")
}