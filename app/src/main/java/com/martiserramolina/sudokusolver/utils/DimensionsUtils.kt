package com.martiserramolina.sudokusolver.utils

import android.content.Context

object DimensionsUtils {
    fun dpToPx(context: Context, dp: Float): Float =
        dp * context.resources.displayMetrics.density
}