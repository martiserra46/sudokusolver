package com.martiserramolina.sudokusolver.mvc.view.viewsmvc.factory

import android.view.LayoutInflater
import com.martiserramolina.sudokusolver.R
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.SudokuViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {
    fun newMainViewMvc() = SudokuViewMvc(layoutInflater, R.layout.activity_sudoku, null)
}