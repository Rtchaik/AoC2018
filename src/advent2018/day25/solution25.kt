package advent2018.day25

import kotlin.math.abs
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
        val newStars = remaining.filter { star -> current.count { distance(star, it) <= 3 } > 0 }
        if (newStars.isNotEmpty()) constellations(remaining - newStars, newStars, count)
        else constellations(remaining.drop(1), listOf(remaining[0]), count.inc())
    }

private fun distance(star1: List<Int>, star2: List<Int>) = (0..3).sumBy { abs(star1[it] - star2[it]) }