package advent2018.day14

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val part1 = generateRecipes().drop(input.toInt()).take(10).joinToString("")
        val part2 = generateRecipes().windowed(input.length).indexOfFirst { window -> window == input.map { it } }

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun generateRecipes() = sequence {
    val recipes = mutableListOf(3, 7)
    yield('3')
    yield('7')
    var elf1Pos = 0
    var elf2Pos = 1

    while (true) {
        (recipes[elf1Pos] + recipes[elf2Pos]).toString().forEach {
            yield(it)
            recipes.add(it.toString().toInt())
        }
        elf1Pos = (elf1Pos + recipes[elf1Pos] + 1) % recipes.size
        elf2Pos = (elf2Pos + recipes[elf2Pos] + 1) % recipes.size
    }
}