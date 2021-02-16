package com.martiserramolina.sudokusolver.mvc.view.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.martiserramolina.sudokusolver.R
import com.martiserramolina.sudokusolver.mvc.model.Sudoku
import com.martiserramolina.sudokusolver.utils.DimensionsUtils

class DrawSudokuUseCase(
        private val sudoku: Sudoku,
        private val sudokuDimensions: SudokuDimensions,
        private val context: Context
) {

    class Design(
        val backgroundColor: Int,
        val squaresPaint: Paint,
        val gridSeparatorPaint: Paint,
        val textValuePaint: TextPaint
    )

    class Shapes(
        val textValueRect: Rect
    )

    private lateinit var canvas: Canvas

    private val design: Design = Design(
        backgroundColor = ContextCompat.getColor(context, R.color.white),
        squaresPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.light_grey)
        },
        gridSeparatorPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.dark_grey)
            strokeWidth = 2f
        },
        textValuePaint = TextPaint().apply {
            textSize = DimensionsUtils.dpToPx(context, 24f)
        }
    )

    private val shapes = Shapes(textValueRect = Rect())

    fun drawSudoku(canvas: Canvas) {
        this.canvas = canvas
        drawWhiteBackground()
        drawSquares()
        drawGridLines()
        drawNumbers()
    }

    private fun drawWhiteBackground() {
        canvas.drawColor(design.backgroundColor)
    }

    private fun drawSquares() {
        for (linePos in 0..8) {
            drawHorizontalLineOfSquares(linePos)
        }
    }

    private fun drawGridLines() {
        for (i in 0..3) drawGridHorizontalLine(i)
        for (i in 0..3) drawGridVerticalLine(i)
    }

    private fun drawNumbers() {
        sudoku.forEach { row, column -> drawValue(row, column) }
    }

    private fun drawHorizontalLineOfSquares(linePos: Int) {
        val squarePositions = if (linePos % 2 == 0) 0..8 step 2 else 1..8 step 2
        for (squarePos in squarePositions) {
            drawSquare(linePos, squarePos)
        }
    }

    private fun drawSquare(linePos: Int, squarePos: Int) {
        val top = sudokuDimensions.sudokuTop + linePos * sudokuDimensions.squareSize
        val bottom = top + sudokuDimensions.squareSize
        val left = sudokuDimensions.sudokuLeft + squarePos * sudokuDimensions.squareSize
        val right = left + sudokuDimensions.squareSize
        canvas.drawRect(left, top, right, bottom, design.squaresPaint)
    }

    private fun drawGridHorizontalLine(i: Int) {
        val startX = sudokuDimensions.sudokuLeft
        val stopX = sudokuDimensions.sudokuLeft + sudokuDimensions.sudokuSize
        val y = sudokuDimensions.sudokuTop + sudokuDimensions.gridSize * i

        canvas.drawLine(startX, y, stopX, y, design.gridSeparatorPaint)
    }

    private fun drawGridVerticalLine(i: Int) {
        val startY = sudokuDimensions.sudokuTop
        val stopY = sudokuDimensions.sudokuTop + sudokuDimensions.sudokuSize
        val x = sudokuDimensions.sudokuLeft + sudokuDimensions.gridSize * i

        canvas.drawLine(x, startY, x, stopY, design.gridSeparatorPaint)
    }

    private fun drawValue(row: Int, column: Int) {
        val value = sudoku.get(row, column)
        if (value == 0) return

        val text = value.toString()

        design.textValuePaint.getTextBounds(text, 0, text.length, shapes.textValueRect)
        val textWidth = shapes.textValueRect.width()
        val textHeight = shapes.textValueRect.height()

        val x = sudokuDimensions.sudokuLeft + (sudokuDimensions.squareSize * column) + (sudokuDimensions.squareSize / 2f - textWidth / 2f)
        val y = sudokuDimensions.sudokuTop + (sudokuDimensions.squareSize * row) + (sudokuDimensions.squareSize / 2f + textHeight / 2f)

        canvas.drawText(text, x, y, design.textValuePaint)
    }

}