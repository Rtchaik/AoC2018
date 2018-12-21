package advent2018.day21

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val instructions = input.drop(1).map { it.split(" ").drop(1).map { i -> i.toInt() } }
        var reg3 = 0
        val halts = mutableListOf<Int>()
        while (!halts.contains(reg3)) {
            halts.add(reg3)
            var reg2 = reg3 or instructions[6][1]//65536
            reg3 = instructions[7][0]//1505483
            while (reg2 >= instructions[13][0]) {//256
                if (reg3 != instructions[7][0]) reg2 /= instructions[19][1]//1505483; 256
                reg3 = ((((reg2 and instructions[8][1]) + reg3) and instructions[10][1])//255; 16777215
                        * instructions[11][1]) and instructions[12][1]//65899; 16777215
            }
        }
        val part1 = halts[1]
        val part2 = halts.last()
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}