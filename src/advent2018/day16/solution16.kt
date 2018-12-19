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
                        opcodes.map { opcodes(it, parsedInput[idx + 1].drop(1), parsedInput[idx].toIntArray()) }
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
            .forEach { part2 = opcodes(registersMap.getOrDefault(it[0], ""), it.drop(1), part2.toIntArray()).second }

        println("Part 1: $part1\nPart 2: ${part2[0]}")
    }
    println("Execution Time = $executionTime ms")
}

fun opcodes(code: String, instruction: List<Int>, registers: IntArray): Pair<String, List<Int>> {
    registers[instruction[2]] = when (code) {
        "addr" -> registers[instruction[0]] + registers[instruction[1]]
        "addi" -> registers[instruction[0]] + instruction[1]
        "mulr" -> registers[instruction[0]] * registers[instruction[1]]
        "muli" -> registers[instruction[0]] * instruction[1]
        "banr" -> registers[instruction[0]] and registers[instruction[1]]
        "bani" -> registers[instruction[0]] and instruction[1]
        "borr" -> registers[instruction[0]] or registers[instruction[1]]
        "bori" -> registers[instruction[0]] or instruction[1]
        "setr" -> registers[instruction[0]]
        "seti" -> instruction[0]
        "gtir" -> if (instruction[0] > registers[instruction[1]]) 1 else 0
        "gtri" -> if (registers[instruction[0]] > instruction[1]) 1 else 0
        "gtrr" -> if (registers[instruction[0]] > registers[instruction[1]]) 1 else 0
        "eqir" -> if (instruction[0] == registers[instruction[1]]) 1 else 0
        "eqri" -> if (registers[instruction[0]] == instruction[1]) 1 else 0
        "eqrr" -> if (registers[instruction[0]] == registers[instruction[1]]) 1 else 0
        else -> throw IllegalArgumentException("Wrong opcode")
    }
    return code to registers.toList()
}