package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.shortybin.form.bean.Module

abstract class FormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    lateinit var formView: ViewGroup
    lateinit var boxView: ViewGroup
    lateinit var formModule: Module

    abstract fun layoutId(): Int
    abstract fun verify(): Boolean
    abstract fun getContent(): Map<String, String>
    abstract fun setData(module: Module)

    init {
        initView()
    }

    private fun initView() {
        formView = inflate(context, layoutId(), null) as ViewGroup
        addView(formView)
    }
}