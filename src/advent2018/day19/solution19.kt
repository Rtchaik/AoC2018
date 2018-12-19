package advent2018.day19

import advent2018.day16.opcodes
import utils.divisorsList
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val instructions = input.map { it.split(" ") }.drop(1).map { it[0] to it.drop(1).map { i -> i.toInt() } }
        var registers = (0..5).map { 0 }.toMutableList()
        val jumpIdx = input[0].takeLast(1).toInt()

        val opcodesJumps = listOf("addr", "addi", "setr", "seti")
        var ip = 0
        while (ip in 0 until instructions.size) {
            if (opcodesJumps.contains(instructions[ip].first)) {
                registers = opcodes(instructions[ip].first, instructions[ip].second, registers.toIntArray())
                    .second.toMutableList()
                ip = registers[jumpIdx]
            } else registers = opcodes(instructions[ip].first, instructions[ip].second, registers.toIntArray())
                .second.toMutableList()
            ip++
            registers[jumpIdx] = ip
        }

        val part1 = registers[0]

        val part2 = divisorsList(10551425).sum()

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}