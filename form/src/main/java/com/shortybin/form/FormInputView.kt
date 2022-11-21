package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import com.shortybin.form.bean.Module
import kotlinx.android.synthetic.main.form_input_view.view.*
import java.util.regex.Pattern

class FormInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormView(context, attrs) {
    override fun layoutId(): Int {
        return R.layout.form_input_view
    }

    override fun verify(): Boolean {
        formModule.regx_list.forEach {
            val pattern = Pattern.compile(it.expression)
            if (!pattern.matcher(form_edit_text.text).matches()) {
                return false
            }
        }
        return true
    }

    override fun getContent(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map[formModule.field] = form_edit_text.text.toString()
        return map
    }

    override fun setData(module: Module) {

    }
}