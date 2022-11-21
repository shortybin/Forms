package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import com.shortybin.form.bean.Module
import kotlinx.android.synthetic.main.form_droop_down_check_box_view.view.*

class FormDropDownCheckBoxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormView(context, attrs) {
    override fun layoutId(): Int {
        return R.layout.form_droop_down_check_box_view
    }

    override fun verify(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getContent(): Map<String, String> {
        TODO("Not yet implemented")
    }

    override fun setData(module: Module) {
        drop_down_text_view.setOnClickListener {

        }
    }

}