package minesweeper

import kotlin.random.Random
import kotlin.math.*

fun main() {

    println("How many mines do you want on the field? ")
    val mines: Int = readLine()!!.toInt()

    val minefield = Minefield(9,9)

    minefield.generateMines(mines)

    minefield.generateHints()

    minefield.printField()

}

