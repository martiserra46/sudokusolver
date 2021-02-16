package com.martiserramolina.sudokusolver.mvc.view.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.martiserramolina.sudokusolver.mvc.model.Sudoku
import com.martiserramolina.sudokusolver.utils.DimensionsUtils

class SudokuView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    fun interface PositionSelectedListener {
        fun onPositionSelected(row: Int, column: Int)
    }

    companion object {
        private const val DESIRED_SIZE_DP = 360f
    }

    private val sudoku = Sudoku()

    private var positionSelectedListeners = mutableListOf<PositionSelectedListener>()

    private val sudokuDimensions = SudokuDimensions()

    private val drawSudokuUseCase = DrawSudokuUseCase(sudoku, sudokuDimensions, context)

    fun sudoku() = sudoku.clone()

    fun set(row: Int, column: Int, value: Int): Boolean {
        val result = sudoku.set(row, column, value)
        invalidate()
        return result
    }

    fun get(row: Int, column: Int) = sudoku.get(row, column)

    fun set(newSudoku: Sudoku) {
        sudoku.reset()
        sudoku.forEach { row, column ->
            sudoku.set(row, column, newSudoku.get(row, column))
        }
        invalidate()
    }

    fun reset() {
        sudoku.reset()
        invalidate()
    }

    fun addOnPositionSelectedListener(positionSelectedListener: PositionSelectedListener) {
        positionSelectedListeners.add(positionSelectedListener)
    }

    fun removeOnPositionSelectedListener(positionSelectedListener: PositionSelectedListener) {
        positionSelectedListeners.remove(positionSelectedListener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = DimensionsUtils.dpToPx(context, DESIRED_SIZE_DP)

        val width = resolveSize(desiredSize.toInt(), widthMeasureSpec)
        val height = resolveSize(desiredSize.toInt(), heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        sudokuDimensions.apply {
            sudokuSize = minOf(width, height).toFloat()
            gridSize = sudokuSize / 3f
            squareSize = sudokuSize / 9f
            sudokuTop = height / 2f - sudokuSize / 2f
            sudokuLeft = width / 2f - sudokuSize / 2f
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawSudokuUseCase.drawSudoku(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                val row = computeRow(event.y)
                val column = computeColumn(event.x)
                if (row in 0..8 && column in 0..8)
                    onPositionSelected(row, column)
                return true
            }
            else -> super.onTouchEvent(event)
        }
    }

    private fun computeRow(y: Float): Int {
        return ((y - sudokuDimensions.sudokuTop) / sudokuDimensions.sudokuSize * 9).toInt()
    }

    private fun computeColumn(x: Float): Int {
        return ((x - sudokuDimensions.sudokuLeft) / sudokuDimensions.sudokuSize * 9).toInt()
    }

    private fun onPositionSelected(row: Int, column: Int) {
        positionSelectedListeners.forEach {
            it.onPositionSelected(row, column)
        }
    }

}