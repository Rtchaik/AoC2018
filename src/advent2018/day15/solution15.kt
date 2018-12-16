package advent2018.day15

import utils.Coordinates
import utils.directions
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val executionTime = measureTimeMillis {
        val startingUnits = input.mapIndexed { rowIdx, row ->
            row.mapIndexedNotNull { columnIdx, item ->
                if (item == 'G' || item == 'E') Triple(item, Coordinates(columnIdx, rowIdx), 200)
                else null
            }
        }.filter { it.isNotEmpty() }.flatten()

        val part1 = beginBattle(startingUnits.toMutableList(), input.map { it.toMutableList() })

        var elfPower = 4
        var part2 = 0 to true
        while (part2.second) {
            part2 = beginBattle(
                startingUnits.toMutableList(),
                input.map { it.toMutableList() },
                elfPower,
                true
            )
            elfPower++
        }

        println("Part 1: ${part1.first}\nPart 2: ${part2.first}")
    }
    println("Execution Time = $executionTime ms")
}

private fun beginBattle(
    units: MutableList<Triple<Char, Coordinates, Int>>,
    grid: List<MutableList<Char>>,
    elfPower: Int = 3,
    noDeadElves: Boolean = false
): Pair<Int, Boolean> {
    var round = 0
    var deadElf = false
    loop@ while (units.groupBy { it.first }.size > 1) {
        units.sortWith(compareBy({ it.second.row }, { it.second.column }))
        var unitIdx = 0
        while (unitIdx < units.size) {
            val current = units[unitIdx]
            if (units.groupBy { it.first }.size == 1) break@loop
            var newSquare =
                makeMove(
                    if (current.first == 'G') 'E' else 'G',
                    grid,
                    listOf(listOf(current.second)),
                    listOf(current.second)
                )
            if (newSquare == null || grid[newSquare.row][newSquare.column] != '.') newSquare = current.second else {
                grid[newSquare.row][newSquare.column] = current.first
                grid[current.second.row][current.second.column] = '.'
            }
            units[unitIdx] = Triple(current.first, newSquare, current.third)
            //battle
            val adjacent = directions.map { it + newSquare }
            val enemies =
                adjacent.filter { grid[it.row][it.column] == if (current.first == 'G') 'E' else 'G' }
            if (enemies.isNotEmpty()) {
                val currentEnemy = units.filter { enemies.contains(it.second) }
                    .sortedWith(compareBy({ it.third }, { it.second.row }, { it.second.column }))[0]
                val enemyIdx = units.indexOf(currentEnemy)
                units[enemyIdx] =
                        Triple(
                            currentEnemy.first,
                            currentEnemy.second,
                            currentEnemy.third - if (currentEnemy.first == 'G') elfPower else 3
                        )
                if (units[enemyIdx].third <= 0) {
                    grid[currentEnemy.second.row][currentEnemy.second.column] = '.'
                    units.removeAt(enemyIdx)
                    if (currentEnemy.first == 'E' && noDeadElves) {
                        deadElf = true
                        break@loop
                    }
                    if (enemyIdx < unitIdx) unitIdx--
                }
            }
            unitIdx++
        }
        round++
    }
    return round * units.sumBy { it.third } to deadElf
}

private tailrec fun makeMove(
    enemy: Char, grid: List<List<Char>>, paths: List<List<Coordinates>?>, visited: List<Coordinates> = listOf(),
    closestEnemies: MutableList<List<Coordinates>> = mutableListOf()
): Coordinates? = when {
    paths.isEmpty() -> null
    paths.contains(null) -> closestEnemies.sortedWith(
        compareBy({ it.last().row }, { it.last().column }, { it[1].row }, { it[1].column })
    )[0][1]
    else -> {
        val newPaths = mutableListOf<List<Coordinates>?>()
        paths.forEach { current ->
            val adjacent = directions.map { it + current!!.last() } - visited
            if (adjacent.isNotEmpty()) {
                val enemies = adjacent.filter { grid[it.row][it.column] == enemy }
                if (enemies.isNotEmpty()) {
                    newPaths.add(null)
                    enemies.forEach { closestEnemies.add(current!! + listOf(it)) }
                } else
                    adjacent.filter { grid[it.row][it.column] == '.' }
                        .forEach { newPaths.add(current!! + listOf(it)) }
            }
        }
        makeMove(enemy, grid, if (newPaths.isNotEmpty() && !newPaths.contains(null)) {
            newPaths.groupBy { it!!.last() }.map { group ->
                group.value.sortedWith(
                    compareBy(
                        { it!![1].row },
                        { it!![1].column })
                )[0]
            }
        } else newPaths, (visited + newPaths.mapNotNull { it?.last() }).distinct(), closestEnemies
        )
    }
}