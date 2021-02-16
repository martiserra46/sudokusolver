package com.martiserramolina.sudokusolver.mvc.controller.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.martiserramolina.sudokusolver.mvc.controller.components.ActivityDependencyGraph
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.base.BaseViewMvc
import com.martiserramolina.sudokusolver.mvc.view.viewsmvc.factory.ViewMvcFactory

abstract class BaseActivity<
        VIEW_MVC : BaseViewMvc<VIEW_MVC_LISTENER>,
        VIEW_MVC_LISTENER : BaseViewMvc.Listener
    > : AppCompatActivity() {

    protected lateinit var dependencyGraph: ActivityDependencyGraph

    private lateinit var viewMvcFactory: ViewMvcFactory

    protected lateinit var viewMvc: VIEW_MVC

    protected lateinit var viewMvcListener: VIEW_MVC_LISTENER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dependencyGraph = ActivityDependencyGraph(this)

        viewMvcFactory = dependencyGraph.viewMvcFactory

        viewMvc = obtainViewMvc(viewMvcFactory)

        viewMvcListener = obtainViewMvcListener()

        setContentView(viewMvc.rootView)
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(viewMvcListener)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(viewMvcListener)
    }

    abstract fun obtainViewMvc(viewMvcFactory: ViewMvcFactory): VIEW_MVC

    abstract fun obtainViewMvcListener(): VIEW_MVC_LISTENER
}