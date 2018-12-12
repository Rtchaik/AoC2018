package advent2018.day12

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val rules = input.map { it.split(" => ") }.associate { it[0] to it[1] }

        var part1 = Pair("....$initialState....", 4)
        repeat(generations1) { part1 = nextGeneration(part1.first, part1.second, rules) }

        var part2 = Pair("....$initialState....", 4)
        var part2Old = Pair("", 0)
        var count = 0
        while (part2.first != part2Old.first) {
            part2Old = part2
            part2 = nextGeneration(part2.first, part2.second, rules)
            count++
        }

        println(
            "Part 1: ${sumOfPlants(part1.first, part1.second.toLong())}\n" +
                    "Part 2: ${sumOfPlants(part2.first, count - generations2 + part2.second)}"
        )
    }
    println("Execution Time = $executionTime ms")
}

private fun sumOfPlants(state: String, shift: Long) =
    state.foldIndexed(0L) { idx, acc, c -> if (c == '#') acc + idx - shift else acc }

private fun nextGeneration(oldGen: String, shift: Int, rules: Map<String, String>): Pair<String, Int> {
    var newGen = oldGen.take(2)
    (2 until oldGen.length - 2).forEach { idx ->
        newGen += rules.getOrDefault(oldGen.substring(idx - 2, idx + 3), '.')
    }
    newGen += oldGen.takeLast(2)
    var shiftAdj = 0
    if (newGen.take(4).contains('#')) {
        newGen = ".$newGen"
        shiftAdj++
    } else if (!newGen.take(5).contains('#')) {
        newGen = newGen.drop(1)
        shiftAdj--
    }
    if (newGen.takeLast(4).contains('#')) newGen += "."
    return newGen to shiftAdj + shift
}