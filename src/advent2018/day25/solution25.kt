package advent2018.day25

import utils.distance
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val stars = input.map { star -> star.split(",").map { it.trim().toInt() } }
        println("Part 1: ${constellations(stars.drop(1), listOf(stars[0]))}")
    }
    println("Execution Time = $executionTime ms")
}

private tailrec fun constellations(remaining: List<List<Int>>, current: List<List<Int>>, count: Int = 1): Int =
    if (remaining.isEmpty()) count else {
        val newStars = remaining.filter { star -> !current.find { distance(star, it) <= 3 }.isNullOrEmpty() }
        if (newStars.isNotEmpty()) constellations(remaining - newStars, newStars, count)
        else constellations(remaining.drop(1), listOf(remaining[0]), count.inc())
    }