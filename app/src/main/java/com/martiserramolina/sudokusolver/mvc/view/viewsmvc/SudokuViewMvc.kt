package com.martiserramolina.sudokusolver.mvc.view.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import com.martiserramolina.sudokusolver.R
import com.martiserramolina.sudokusolver.mvc.model.Sudoku
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.base.BaseViewMvc
import com.martiserramolina.sudokusolver.mvc.view.customviews.SudokuView

class SudokuViewMvc(
    layoutInflater: LayoutInflater,
    @LayoutRes layoutId: Int,
    parent: ViewGroup?
) : BaseViewMvc<SudokuViewMvc.Listener>(layoutInflater, layoutId, parent) {

    interface Listener : BaseViewMvc.Listener {
        fun onSudokuPositionSelected(row: Int, column: Int)
        fun onSelectedValueChanged(selectedValue: Int) {}
        fun onSolveButtonClicked()
        fun onResetButtonClicked()
    }

    private val sudokuView: SudokuView = findViewById(R.id.sudoku)
    private val valuesRadioGroup: RadioGroup = findViewById(R.id.values_radio_group)
    private val resetButton: Button = findViewById(R.id.reset)
    private val solveButton: Button = findViewById(R.id.solve)

    var selectedValue = 0

    init {
        sudokuView.addOnPositionSelectedListener { row, column ->
            for (listener in listeners)
                listener.onSudokuPositionSelected(row, column)
        }
        valuesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedValue = valueFromRadioButtonId(checkedId)
            for (listener in listeners)
                listener.onSelectedValueChanged(selectedValue)
        }
        solveButton.setOnClickListener {
            for (listener in listeners)
                listener.onSolveButtonClicked()
        }
        resetButton.setOnClickListener {
            for (listener in listeners)
                listener.onResetButtonClicked()
        }
    }

    fun getSudoku(): Sudoku = sudokuView.sudoku()

    fun setValueInSudoku(row: Int, column: Int, value: Int): Boolean {
        return sudokuView.set(row, column, value)
    }

    fun updateSudoku(sudoku: Sudoku) {
        sudokuView.set(sudoku)
    }

    fun resetSudoku() {
        sudokuView.reset()
    }

    private fun valueFromRadioButtonId(@IdRes id: Int): Int {
        return when (id) {
            R.id.zero -> 0
            R.id.one -> 1
            R.id.two -> 2
            R.id.three -> 3
            R.id.four -> 4
            R.id.five -> 5
            R.id.six -> 6
            R.id.seven -> 7
            R.id.eight -> 8
            else -> 9
        }
    }

}