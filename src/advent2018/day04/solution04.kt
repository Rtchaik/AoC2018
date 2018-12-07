package advent2018.day04

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val records = shiftAnalysis(input)
        val part1 = idMultiple(records.maxBy { it.value.sum() }!!)
        val part2 = idMultiple(records.maxBy { it.value.max()!! }!!)
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun idMultiple(mostSleepy: Map.Entry<String, List<Int>>) =
    mostSleepy.key.toInt() * mostSleepy.value.indexOf(mostSleepy.value.max())

private fun shiftAnalysis(input: List<String>): Map<String, List<Int>> {
    val shifts = input.sorted().map { item -> item.split("""\s|]|\[|#""".toRegex()).filter { it.isNotBlank() } }
    val records = mutableMapOf<String, List<Int>>()
    var guard = "0"
    var start = 0
    var finish: Int
    shifts.forEach {
        when (it[2]) {
            "Guard" -> {
                if (!records.containsKey(it[3])) records[it[3]] = (0..59).map { 0 }
                guard = it[3]
            }
            "falls" -> start = it[1].takeLast(2).toInt()
            "wakes" -> {
                finish = it[1].takeLast(2).toInt()
                records[guard] =
                        records[guard]!!.mapIndexed { idx, i -> if (idx in start until finish) i + 1 else i }
            }
        }
    }
    return records
}