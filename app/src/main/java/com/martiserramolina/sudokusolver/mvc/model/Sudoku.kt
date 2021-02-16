package com.martiserramolina.sudokusolver.mvc.model

class Sudoku {

    private val values = Array(9) { Array(9) { 0 } }

    fun set(row: Int, column: Int, value: Int): Boolean {
        if (checkValueValid(row, column, value).not()) return false
        values[row][column] = value
        return true
    }

    fun get(row: Int, column: Int) = values[row][column]

    fun reset() {
        forEach { row, column -> values[row][column] = 0 }
    }

    fun forEach(func: (row: Int, column: Int) -> Unit) {
        for (row in values.indices) {
            for (column in values.indices) {
                func(row, column)
            }
        }
    }

    fun clone(): Sudoku {
        val newSudoku = Sudoku()
        newSudoku.forEach { row, column ->
            newSudoku.set(row, column, values[row][column])
        }
        return newSudoku
    }

    fun isSolved(): Boolean {
        return values.none { it.contains(0) }
    }

    fun checkValueValid(row: Int, column: Int, value: Int): Boolean {
        if (value == 0) return true
        if (values[row][column] != 0) return false
        if (checkValueInRow(row, value)) return false
        if (checkValueInColumn(column, value)) return false
        if (checkValueInGrid(row, column, value)) return false
        return true
    }

    private fun checkValueInRow(row: Int, value: Int): Boolean {
        return values[row].contains(value)
    }

    private fun checkValueInColumn(column: Int, value: Int): Boolean {
        return values.any { it[column] == value }
    }

    private fun checkValueInGrid(row: Int, column: Int, value: Int): Boolean {
        val gridRow = row / 3
        val gridColumn = column / 3

        val firstRow = gridRow * 3
        val lastRow = firstRow + 2

        val firstColumn = gridColumn * 3
        val lastColumn = firstColumn + 2

        for (i in firstRow..lastRow) {
            for (j in firstColumn..lastColumn) {
                if (values[i][j] == value) return true
            }
        }
        return false
    }
}