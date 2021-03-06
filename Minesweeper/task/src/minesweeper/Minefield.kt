package minesweeper

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object Minefield  {

    private const val width = 9
    private const val height = 9

    private val grid = Array(height) {
        Array(width) { '.' }
    }

    fun generateMines(numberOfMines:Int){
        repeat(numberOfMines){
            var empty = true
            while (empty){
                val x = Random.nextInt(9)
                val y = Random.nextInt(9)

                empty = !toggleCell(x, y)

            }
        }

    }

    private fun toggleCell(x: Int, y: Int, value: Char ='X'): Boolean{

        if (this.grid[y][x] == '.'){
            this.grid[y][x] = value
            return true
        }
        return false
    }

    private fun calculateBombNeighbors(x:Int, y:Int):Int {
        val rows = this.grid.size
        val cols = this.grid[0].size

        var neighbors = 0

        for (ny in max( 0 , y - 1) until min(rows, y + 2)){
            for (nx in max(0,x - 1) until min(cols, x + 2)){
                if (!(ny == y && nx == x)){
                    if (this.grid[ny][nx] == 'X'){
                        neighbors ++
                    }
                }
            }
        }

        return neighbors
    }

    fun generateHints(){
        for (y in this.grid.indices){
            for (x in this.grid[0].indices){
                val bombNeighbors =  calculateBombNeighbors(x,y )

                if (bombNeighbors > 0) {
                    toggleCell(x, y ,bombNeighbors.toString()[0])

                }

            }
        }
    }

    fun printField(){
        for (row in this.grid){
            println(row.joinToString(separator = ""))
        }
    }
}