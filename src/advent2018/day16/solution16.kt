package advent2018.day16

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val parsedInput =
            input.map { line -> line.split("""\D+""".toRegex()).filter { it.isNotBlank() }.map { it.toInt() } }

        val part1 = (0..parsedInput.size step 4)
            .map { idx ->
                (0..15).map { opcodes(it, parsedInput[idx + 1], parsedInput[idx].toIntArray()) }
                    .filter { it.second == parsedInput[idx + 2] }.count()
            }
            .count { it >= 3 }

        val registersMap = mutableMapOf<Int, Int>()
        val processedInput = (0..parsedInput.size step 4)
            .map { idx ->
                parsedInput[idx + 1][0] to
                        (0..15).map { opcodes(it, parsedInput[idx + 1], parsedInput[idx].toIntArray()) }
                            .filter { it.second == parsedInput[idx + 2] }.toMutableList()
            }.toMutableList()
        while (processedInput.isNotEmpty()) {
            processedInput.filter { it.second.size == 1 }.map { it.first to it.second[0].first }.distinct()
                .forEach { pair ->
                    registersMap[pair.first] = pair.second
                    processedInput.removeAll { it.first == pair.first }
                    processedInput.forEach { item -> item.second.removeIf { it.first == pair.second } }
                }
        }

        var part2 = (0..3).map { 0 }
        testProg.map { line -> line.split("""\s""".toRegex()).map { it.toInt() } }
            .forEach { part2 = opcodes(registersMap.getOrDefault(it[0], 0), it, part2.toIntArray()).second }

        println("Part 1: $part1\nPart 2: ${part2[0]}")
    }
    println("Execution Time = $executionTime ms")
}

private fun opcodes(code: Int, instruction: List<Int>, registers: IntArray): Pair<Int, List<Int>> {
    registers[instruction[3]] = when (code) {
        0 -> registers[instruction[1]] + registers[instruction[2]]//addr
        1 -> registers[instruction[1]] + instruction[2]//addi
        2 -> registers[instruction[1]] * registers[instruction[2]]//mulr
        3 -> registers[instruction[1]] * instruction[2]//muli
        4 -> registers[instruction[1]] and registers[instruction[2]]//banr
        5 -> registers[instruction[1]] and instruction[2]//bani
        6 -> registers[instruction[1]] or registers[instruction[2]]//borr
        7 -> registers[instruction[1]] or instruction[2]//bori
        8 -> registers[instruction[1]]//setr
        9 -> instruction[1]//seti
        10 -> if (instruction[1] > registers[instruction[2]]) 1 else 0//gtir
        11 -> if (registers[instruction[1]] > instruction[2]) 1 else 0//gtri
        12 -> if (registers[instruction[1]] > registers[instruction[2]]) 1 else 0//gtrr
        13 -> if (instruction[1] == registers[instruction[2]]) 1 else 0//eqir
        14 -> if (registers[instruction[1]] == instruction[2]) 1 else 0//eqri
        15 -> if (registers[instruction[1]] == registers[instruction[2]]) 1 else 0//eqrr
        else -> registers[instruction[3]]
    }
    return code to registers.toList()
}