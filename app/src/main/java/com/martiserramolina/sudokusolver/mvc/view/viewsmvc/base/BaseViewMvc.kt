package com.martiserramolina.sudokusolver.mvc.view.viewsmvc.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar

abstract class BaseViewMvc<LISTENER_TYPE : BaseViewMvc.Listener>(
    layoutInflater: LayoutInflater,
    @LayoutRes layoutId: Int,
    parent: ViewGroup?
) {

    interface Listener

    val rootView: View = layoutInflater.inflate(layoutId, parent, false)

    protected val context = rootView.context

    protected val listeners = mutableSetOf<LISTENER_TYPE>()

    fun registerListener(listener: LISTENER_TYPE) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_TYPE) {
        listeners.remove(listener)
    }

    fun showMessage(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    protected fun <T : View?> findViewById(@IdRes id: Int): T = rootView.findViewById<T>(id)

}