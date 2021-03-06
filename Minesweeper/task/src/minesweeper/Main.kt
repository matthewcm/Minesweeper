package minesweeper

fun main() {

    println("How many mines do you want on the field? ")
    val mines: Int = readLine()!!.toInt()

    Minefield.generateMines(mines)

    Minefield.generateHints()

    Minefield.printField()

    Minefield.play()
}

