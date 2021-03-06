package minesweeper

import kotlin.random.Random
import kotlin.math.*

fun main() {

    val width = 9
    val height = 9

    println("How many mines do you want on the field? ")
    val mines: Int = readLine()!!.toInt()

    val minefield = Array(height) {
        Array(width) { '.' }
    }

    generateMines(minefield, mines)

    generateHints(minefield)

    printField(minefield)

}

fun generateMines(minefield: Array<Array<Char>>, numberOfMines:Int){
    repeat(numberOfMines){
        var empty = true
        while (empty){
            val x = Random.nextInt(9)
            val y = Random.nextInt(9)

            empty = !toggleCell(x, y, minefield)

        }
    }

}

fun toggleCell(x: Int, y: Int,  minefield: Array<Array<Char>>, value: Char ='X'): Boolean{

    if (minefield[y][x] == '.'){
        minefield[y][x] = value
        return true
    }
    return false
}

fun calculateBombNeighbors(x:Int, y:Int,  minefield: Array<Array<Char>>):Int {
    val rows = minefield.size
    val cols = minefield[0].size

    var neighbors = 0

    for (ny in max( 0 , y - 1) until min(rows, y + 2)){
        for (nx in max(0,x - 1) until min(cols, x + 2)){
            if (!(ny == y && nx == x)){
                if (minefield[ny][nx] == 'X'){
                    neighbors ++
                }
            }
        }
    }

    return neighbors
}

fun generateHints(minefield: Array<Array<Char>>){
    for (y in minefield.indices){
        for (x in minefield[0].indices){
            val bombNeighbors =  calculateBombNeighbors(x,y , minefield)

            if (bombNeighbors > 0) {
                toggleCell(x, y , minefield, bombNeighbors.toString()[0])

            }

        }
    }

}

fun printField(minefield: Array<Array<Char>>){
    for (row in minefield){
        println(row.joinToString(separator = ""))
    }
}
