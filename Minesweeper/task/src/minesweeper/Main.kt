package minesweeper

import kotlin.random.Random
import kotlin.math.*

fun main() {

    println("How many mines do you want on the field? ")
    val mines: Int = readLine()!!.toInt()

    Minefield.generateMines(mines)

    Minefield.generateHints()

    Minefield.printField()

}

