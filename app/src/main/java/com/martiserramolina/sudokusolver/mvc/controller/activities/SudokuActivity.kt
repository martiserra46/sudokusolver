package com.martiserramolina.sudokusolver.mvc.controller.activities

import android.os.Handler
import android.os.Looper
import com.martiserramolina.sudokusolver.R
import com.martiserramolina.sudokusolver.mvc.controller.activities.base.BaseActivity
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.SudokuViewMvc
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.factory.ViewMvcFactory
import com.martiserramolina.sudokusolver.mvc.controller.sudokusolver.SudokuSolver


class SudokuActivity : BaseActivity<SudokuViewMvc, SudokuViewMvc.Listener>(), SudokuViewMvc.Listener {

    private val handler = Handler(Looper.getMainLooper())

    private val sudokuSolver = SudokuSolver()

    override fun obtainViewMvc(viewMvcFactory: ViewMvcFactory) = viewMvcFactory.newMainViewMvc()

    override fun obtainViewMvcListener(): SudokuViewMvc.Listener = this

    override fun onSudokuPositionSelected(row: Int, column: Int) {
        val success = viewMvc.setValueInSudoku(row, column, viewMvc.selectedValue)
        if (success.not()) {
            viewMvc.showMessage(getString(R.string.cannot_insert_value))
        }
    }

    override fun onSolveButtonClicked() {
        Thread {
            val result = sudokuSolver.solve(viewMvc.getSudoku())
            handler.post {
                if (result is SudokuSolver.Result.Success) {
                    viewMvc.updateSudoku(result.sudoku)
                    viewMvc.showMessage(getString(R.string.solve_success))
                } else {
                    viewMvc.showMessage(getString(R.string.solve_failure))
                }
            }
        }.start()
    }

    override fun onResetButtonClicked() {
        viewMvc.resetSudoku()
    }

}