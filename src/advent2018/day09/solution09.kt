package advent2018.day09

import java.util.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val parsedInput = input.split("""\D+""".toRegex()).dropLast(1).map { it.toLong() }
        val part1 = runGame(parsedInput)
        val part2 = runGame(listOf(parsedInput[0], parsedInput[1] * 100))
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun runGame(rules: List<Long>): Long? {
    val marbles = ArrayDeque<Long>()
    marbles.offer(0L)
    val players = (0 until rules[0]).associate { it to 0L }.toMutableMap()
    (1L..rules[1]).forEach {
        if (it % 23L != 0L) {
            marbles.offer(marbles.poll())
            marbles.offer(it)
        } else {
            repeat(7) { marbles.offerFirst(marbles.pollLast()) }
            players[it % rules[0]] = players[it % rules[0]]!! + it + marbles.pollLast()
            marbles.offer(marbles.poll())
        }
    }
    return players.values.max()
}