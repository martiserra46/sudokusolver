package com.martiserramolina.sudokusolver.mvc.controller.components

import android.content.Context
import android.view.LayoutInflater
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.factory.ViewMvcFactory

class ActivityDependencyGraph(context: Context) {

    private val layoutInflater = LayoutInflater.from(context)

    val viewMvcFactory = ViewMvcFactory(layoutInflater)

}