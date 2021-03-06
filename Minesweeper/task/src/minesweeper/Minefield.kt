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

    private fun toggleCellGrid(x: Int, y: Int, value: Char = 'X'): Boolean {

        if (this.grid[y][x] == '.') {
            this.grid[y][x] = value
            return true
        }
        return false
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
            move()
            printField()

            var numUnexplored = 0
            for (row in this.playerGrid) {
                for (cell in row) {
                    if (cell == '.'){
                        numUnexplored ++
                    }
                }
            }
            if (numUnexplored == hiddenMines){
                hiddenMines = 0
            }
        }

        println("Congratulations! You found all the mines!")
    }

    private fun exploreNeighbors(x:Int, y:Int){
        val rows = this.grid.size
        val cols = this.grid[0].size

        if (this.grid[y][x] == '.') {
            this.playerGrid[y][x] = '/'
        }else if (this.grid[y][x] == 'X') {
        }else {
            this.playerGrid[y][x] = this.grid[y][x]
        }
        for (ny in max(0, y - 1) until min(rows, y + 2)) {
            for (nx in max(0, x - 1) until min(cols, x + 2)) {
                if (!(ny == y && nx == x)) {
                    if (this.grid[ny][nx] != 'X' && (this.playerGrid[ny][nx] == '.' || this.playerGrid[ny][nx] == '*')) {
                        this.exploreNeighbors(nx, ny)
                    }
                }
            }
        }
    }
    private fun explore(x:Int, y: Int) {
        when (this.playerGrid[y][x]) {
            'X'-> {
                println("You stepped on a mine and failed")
//                Show grid with X mines
            }
            else -> {
                exploreNeighbors(x,y)
            }
        }

    }
    private fun mine(x:Int, y: Int) {
        when {
            this.playerGrid[y][x] == '.' -> {
                this.playerGrid[y][x] = '*'

                if (this.grid[y][x] == 'X') {
                    hiddenMines--
                }
            }
            this.playerGrid[y][x] == '*' -> {
                this.playerGrid[y][x] = '.'

                if (this.grid[y][x] == 'X') {
                    hiddenMines++
                }

            }
            else -> {
                println("There is a number here!")
                move()
            }
        }

    }
    private fun move() {

        val scanner = Scanner(System.`in`)
        println("Set/delete mine marks or claim a cell as free: ")
        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1
        val command = scanner.next()

        if (command == "mine"){
            mine(x, y)
        }else if(command == "free"){
            explore(x,y)
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
                    toggleCellGrid(x, y, bombNeighbors.toString()[0])

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