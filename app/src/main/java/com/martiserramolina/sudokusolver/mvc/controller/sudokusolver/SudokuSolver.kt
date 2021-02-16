package com.martiserramolina.sudokusolver.mvc.controller.sudokusolver

import com.martiserramolina.sudokusolver.mvc.model.Sudoku

class SudokuSolver(private val timeLimit: Long = 3000) {

    sealed class Result {
        class Success(val sudoku: Sudoku) : Result()
        object Failure : Result()
    }

    fun solve(sudoku: Sudoku): Result {
        val initTime = System.currentTimeMillis()
        val sudokusToVisit = mutableListOf(sudoku)
        var selectedSudoku: Sudoku? = null
        var solutionFound = false

        while (solutionFound.not() && sudokusToVisit.isNotEmpty() && (System.currentTimeMillis() - initTime) < timeLimit) {
            selectedSudoku = sudokusToVisit.removeAt(0)
            if (selectedSudoku.isSolved()) {
                solutionFound = true
            } else {
                val newSudokus = obtainNewSudokus(selectedSudoku)
                sudokusToVisit.addAll(0, newSudokus)
            }
        }
        return if (solutionFound) Result.Success(selectedSudoku!!) else Result.Failure
    }

    private fun obtainNewSudokus(sudoku: Sudoku): List<Sudoku> {
        val newSudokus = mutableListOf<Sudoku>()
        val (row, column) = findFirstEmptyPosition(sudoku)
        for (value in 1..9) {
            if (sudoku.checkValueValid(row, column, value)) {
                val newSudoku = sudoku.clone()
                newSudoku.set(row, column, value)
                newSudokus.add(newSudoku)
            }
        }
        return newSudokus
    }

    private fun findFirstEmptyPosition(sudoku: Sudoku): Pair<Int, Int> {
        var pos = Pair(-1, -1)
        sudoku.forEach { row, column ->
            if (sudoku.get(row, column) == 0) {
                pos = Pair(row, column)
                return@forEach
            }
        }
        return pos
    }

}