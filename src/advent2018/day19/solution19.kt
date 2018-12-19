package advent2018.day19

import advent2018.day16.opcodes
import utils.divisorsList
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val instructions = input.map { it.split(" ") }.drop(1).map { it[0] to it.drop(1).map { i -> i.toInt() } }
        val jumpReg = input[0].takeLast(1).toInt()

        val part1 = runProgram(instructions, jumpReg)[0]
        val part2 = divisorsList(runProgram(instructions, jumpReg, 1)[3]).sum()
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun runProgram(instructions: List<Pair<String, List<Int>>>, jumpReg: Int, reg0: Int = 0): IntArray {
    var registers = (0..5).map { 0 }.toIntArray()
    registers[0] = reg0
    val opcodesJumps = listOf("addr", "addi", "setr", "seti")
    var ip = 0
    while (ip in 0 until instructions.size) {
        registers = opcodes(instructions[ip].first, instructions[ip].second, registers).second.toIntArray()
        ip = if (opcodesJumps.contains(instructions[ip].first)) registers[jumpReg] + 1 else ip + 1
        registers[jumpReg] = ip
        if (reg0 == 1 && ip == 2) break
    }
    return registers
}