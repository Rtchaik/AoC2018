package advent2018.day16

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val parsedInput =
            input.map { line -> line.split("""\D+""".toRegex()).filter { it.isNotBlank() }.map { it.toInt() } }

        val opcodes = listOf(
            "addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori",
            "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"
        )
        val processedInput = (0..parsedInput.size step 4)
            .map { idx ->
                parsedInput[idx + 1][0] to
                        opcodes.map { opcodes(it, parsedInput[idx + 1], parsedInput[idx].toIntArray()) }
                            .filter { it.second == parsedInput[idx + 2] }.toMutableList()
            }.toMutableList()
        val part1 = processedInput.count { it.second.size >= 3 }

        val registersMap = mutableMapOf<Int, String>()
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
            .forEach { part2 = opcodes(registersMap.getOrDefault(it[0], ""), it, part2.toIntArray()).second }

        println("Part 1: $part1\nPart 2: ${part2[0]}")
    }
    println("Execution Time = $executionTime ms")
}

private fun opcodes(code: String, instruction: List<Int>, registers: IntArray): Pair<String, List<Int>> {
    registers[instruction[3]] = when (code) {
        "addr" -> registers[instruction[1]] + registers[instruction[2]]
        "addi" -> registers[instruction[1]] + instruction[2]
        "mulr" -> registers[instruction[1]] * registers[instruction[2]]
        "muli" -> registers[instruction[1]] * instruction[2]
        "banr" -> registers[instruction[1]] and registers[instruction[2]]
        "bani" -> registers[instruction[1]] and instruction[2]
        "borr" -> registers[instruction[1]] or registers[instruction[2]]
        "bori" -> registers[instruction[1]] or instruction[2]
        "setr" -> registers[instruction[1]]
        "seti" -> instruction[1]
        "gtir" -> if (instruction[1] > registers[instruction[2]]) 1 else 0
        "gtri" -> if (registers[instruction[1]] > instruction[2]) 1 else 0
        "gtrr" -> if (registers[instruction[1]] > registers[instruction[2]]) 1 else 0
        "eqir" -> if (instruction[1] == registers[instruction[2]]) 1 else 0
        "eqri" -> if (registers[instruction[1]] == instruction[2]) 1 else 0
        "eqrr" -> if (registers[instruction[1]] == registers[instruction[2]]) 1 else 0
        else -> throw IllegalArgumentException("Wrong opcode")
    }
    return code to registers.toList()
}