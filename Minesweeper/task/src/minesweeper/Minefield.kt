package minesweeper

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
import java.util.Scanner

object Minefield {

    private const val width = 9
    private const val height = 9

    var hiddenMines = 0

    private val grid = Array(height) {
        Array(width) { '.' }
    }

    private val playerGrid = Array(height) {
        Array(width) { '.' }
    }

    fun generateMines(numberOfMines: Int) {
        repeat(numberOfMines) {
            var empty = true
            while (empty) {
                val x = Random.nextInt(9)
                val y = Random.nextInt(9)

                empty = !toggleCell(x, y)

            }
        }

        hiddenMines = numberOfMines

    }

    private fun toggleCell(x: Int, y: Int, value: Char = 'X'): Boolean {

        if (this.grid[y][x] == '.') {
            this.grid[y][x] = value
            if (value != 'X') {
                this.playerGrid[y][x] = value
            }
            return true
        }
        return false
    }

    fun play() {
        while (hiddenMines > 0) {
            flagCell()
            printField()
        }

        println("Congratulations! You found all the mines!")
    }

    private fun flagCell(): Boolean {

        val scanner = Scanner(System.`in`)
        println("Set/delete mines marks (x and y coordinates): ")
        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1

        return when {
            this.playerGrid[y][x] == '.' -> {
                this.playerGrid[y][x] = '*'

                if (this.grid[y][x] == 'X') {
                    hiddenMines--
                }
                true
            }
            this.playerGrid[y][x] == '*' -> {
                this.playerGrid[y][x] = '.'

                if (this.grid[y][x] == 'X') {
                    hiddenMines++
                }

                true
            }
            else -> {
                println("There is a number here!")
                flagCell()
            }
        }
    }

    private fun calculateBombNeighbors(x: Int, y: Int): Int {
        val rows = this.grid.size
        val cols = this.grid[0].size

        var neighbors = 0

        for (ny in max(0, y - 1) until min(rows, y + 2)) {
            for (nx in max(0, x - 1) until min(cols, x + 2)) {
                if (!(ny == y && nx == x)) {
                    if (this.grid[ny][nx] == 'X') {
                        neighbors++
                    }
                }
            }
        }

        return neighbors
    }

    fun generateHints() {
        for (y in this.grid.indices) {
            for (x in this.grid[0].indices) {
                val bombNeighbors = calculateBombNeighbors(x, y)

                if (bombNeighbors > 0) {
                    toggleCell(x, y, bombNeighbors.toString()[0])

                }

            }
        }
    }

    fun printField() {
        println(" |123456789|")
        println("-|---------|")
        for ((index, row) in this.playerGrid.withIndex()) {

            val rowIndex = index + 1
            println("$rowIndex|" + row.joinToString(separator = "") + "|")
        }
        println("-|---------|")
    }
}