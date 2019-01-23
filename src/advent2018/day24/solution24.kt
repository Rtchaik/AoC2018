package advent2018.day24

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {

        val immune = parseInput("immune", immuneInput)
        val infection = parseInput("infection", infectionInput)

        var battleResult = battle(sortArmy(immune), sortArmy(infection))
        val part1 = (battleResult.first + battleResult.second).sumBy { it.count }

        do {
            immune.forEach { it.count = it.countStart; it.attackDamage++ }
            infection.forEach { it.count = it.countStart }
            battleResult = battle(sortArmy(immune), sortArmy(infection))
        } while (battleResult.second.isNotEmpty())
        val part2 = (battleResult.first + battleResult.second).sumBy { it.count }

        println("Part 1: $part1\nPart 2: $part2")
    }
    println("Execution Time = $executionTime ms")
}

private tailrec fun battle(immune: List<Unit>, infection: List<Unit>, killed: Boolean = true):
        Pair<List<Unit>, List<Unit>> =
    if (immune.isEmpty() || infection.isEmpty() || !killed) Pair(immune, infection) else {
        val immuneSelect = targetSelection(immune, infection)
        val infectSelect = targetSelection(infection, immune)
        var kia = false
        (immune + infection).sortedBy { it.initiative }.reversed().forEach { unit ->
            val enemy = if (unit.armyType == "immune") {
                infection.getOrNull(immuneSelect[immune.indexOfFirst { it.initiative == unit.initiative }])
            } else {
                immune.getOrNull(infectSelect[infection.indexOfFirst { it.initiative == unit.initiative }])
            }
            if (unit.count > 0 && enemy != null) {
                val dead = damage(unit, enemy) / enemy.hitPoints
                if (dead > 0) {
                    kia = true
                    enemy.count -= dead
                }
            }
        }
        battle(sortArmy(immune), sortArmy(infection), kia)
    }

private fun sortArmy(army: List<Unit>) = army.filter { it.count > 0 }
    .sortedWith(compareBy({ it.count * it.attackDamage }, { it.initiative })).reversed()

private fun targetSelection(attack: List<Unit>, defence: List<Unit>): List<Int> {
    val attackList = mutableListOf<Int>()
    attack.forEach { attacking ->
        val remaining = defence.mapIndexedNotNull { idx, unit ->
            val damage = damage(attacking, unit)
            if (idx !in attackList && damage > 0) unit to damage else null
        }.sortedWith(
            compareBy({ it.second },
                { it.first.count * it.first.attackDamage },
                { it.first.initiative })
        )
        if (remaining.isNotEmpty()) attackList.add(defence.indexOf(remaining.last().first)) else attackList.add(-1)
    }
    return attackList
}

private fun damage(attackUnit: Unit, defenceUnit: Unit) = when {
    attackUnit.attackType in defenceUnit.weaknesses -> attackUnit.count * attackUnit.attackDamage * 2
    attackUnit.attackType in defenceUnit.immunities -> 0
    else -> attackUnit.count * attackUnit.attackDamage
}

private class Unit(
    val armyType: String,
    val countStart: Int,
    val hitPoints: Int,
    var attackDamage: Int,
    val attackType: String,
    val initiative: Int,
    val immunities: List<String>,
    val weaknesses: List<String>,
    var count: Int = countStart
)

private fun parseInput(type: String, input: List<String>) =
    input.map { current ->
        val regexTotal =
            """(\d+) units each with (\d+) hit points (\(.+\) )?with an attack that does (\d+) (\w+) damage at initiative (\d+)""".toRegex()
        val (countStart, hitPoints, immuneWeak, attackDamage, attackType, initiative) = regexTotal.matchEntire(current)!!.destructured
        val immune = """immune to((?: \w+,?)+)[;)]""".toRegex().find(immuneWeak)
        val weak = """weak to((?: \w+,?)+)[;)]""".toRegex().find(immuneWeak)
        Unit(
            type,
            countStart.toInt(),
            hitPoints.toInt(),
            attackDamage.toInt(),
            attackType,
            initiative.toInt(),
            if (immune != null) immune.groupValues[1].split(",").map { it.trim() } else emptyList(),
            if (weak != null) weak.groupValues[1].split(",").map { it.trim() } else emptyList()
        )
    }