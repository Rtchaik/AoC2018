package advent2018.day14

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val part1 = generateRecipes().subList(input.toInt(), input.toInt() + 10).joinToString("")
        val recipes = generateRecipes(2)
        val part2 =
            recipes.size - (input.length + 1) + recipes.takeLast(input.length + 1).joinToString("").indexOf(input)

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private tailrec fun generateRecipes(
    part: Int = 1,
    recipes: MutableList<Int> = mutableListOf(3, 7),
    elf1Pos: Int = 0,
    elf2Pos: Int = 1
): List<Int> =
    if (if (part == 1) recipes.size >= input.toInt() + 10
        else recipes.takeLast(input.length + 1).joinToString("").contains(input)
    ) recipes
    else {
        val elf1Recipe = recipes[elf1Pos]
        val elf2Recipe = recipes[elf2Pos]
        val newRecipes = elf1Recipe + elf2Recipe
        val recipe1 = (newRecipes / 10) % 10
        if (recipe1 != 0) recipes.add(recipe1)
        recipes.add(newRecipes % 10)
        generateRecipes(
            part, recipes,
            (elf1Pos + elf1Recipe + 1) % recipes.size, (elf2Pos + elf2Recipe + 1) % recipes.size
        )
    }