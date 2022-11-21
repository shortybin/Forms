package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox
import com.shortybin.form.bean.Module
import kotlinx.android.synthetic.main.form_checkbox_view.view.*

class FormCheckBoxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormView(context, attrs) {
    val contentList = mutableListOf<String>()

    override fun layoutId(): Int {
        return R.layout.form_checkbox_view
    }

    override fun verify(): Boolean {
        return if (formModule.required) {
            contentList.isNotEmpty()
        } else {
            true
        }
    }

    override fun getContent(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map[formModule.field] = contentList.joinToString(",")
        return map
    }

    override fun setData(module: Module) {
        val first = module.resource.first()
        first.options.forEach {
            val checkBoxView = CheckBox(context)
            checkBoxView.text = it.value
            checkBoxView.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    contentList.add(it.value)
                } else {
                    contentList.remove(it.value)
                }
            }
            new_line_linear_layout.addView(checkBoxView)
        }
    }
}