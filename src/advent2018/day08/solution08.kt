package advent2018.day08

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val parsedInput = input.split(" ").map { it.toInt() }
        val result = sumMetadata(parsedInput)

        val part1 = result.second
        val part2 = result.third
        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private fun sumMetadata(inputList: List<Int>): Triple<Int, Int, Int> =
    if (inputList[0] == 0) {
        val nodeValue = inputList.subList(2, 2 + inputList[1]).sum()
        Triple(2 + inputList[1], nodeValue, nodeValue)
    } else {
        var inputSublist = inputList.drop(2)
        var sum = 0
        val valuesChildren = mutableListOf<Int>()
        var length = 0
        repeat(inputList[0]) {
            val child = sumMetadata(inputSublist)
            length += child.first
            sum += child.second
            valuesChildren.add(child.third)
            inputSublist = inputSublist.drop(child.first)
        }
        length += 2 + inputList[1]
        val metadata = inputList.subList(length - inputList[1], length)
        Triple(
            length,
            sum + metadata.sum(),
            metadata.map { if (it - 1 in 0 until valuesChildren.size) valuesChildren[it - 1] else 0 }.sum()
        )
    }
