package advent2018.day21

import advent2018.day16.opcodes
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val instructions = input.map { it.split(" ") }.drop(1).map { it[0] to it.drop(1).map { i -> i.toInt() } }
        val boundReg = input[0].takeLast(1).toInt()

        val halts = runProgram(instructions, boundReg)
        val part1 = halts.first()
        val part2 = halts.last()
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun runProgram(instructions: List<Pair<String, List<Int>>>, boundReg: Int): List<Int> {
    var registers = (0..5).map { 0 }.toIntArray()
    val opcodesJumps = listOf("addr", "addi", "setr", "seti")
    var ip = 0
    val halts = mutableListOf<Int>()
    while (!(ip == 28 && registers[3] in halts)) {
        if (ip == 28) halts.add(registers[3])
        registers[boundReg] = ip
        registers = opcodes(instructions[ip].first, instructions[ip].second, registers).second
        ip = if (opcodesJumps.contains(instructions[ip].first)) registers[boundReg] + 1 else ip + 1
    }
    return halts
}