package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import com.shortybin.form.FormGroupView
import com.shortybin.form.bean.Module
import kotlinx.android.synthetic.main.jw_form_group_view.view.*

class JWFormGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormGroupView(context, attrs) {

    override fun layoutId(): Int {
        return R.layout.jw_form_group_view
    }

    override fun verify(): Boolean {
        return convertView.verify()
    }

    override fun getContent(): Map<String, String> {
        return convertView.getContent()
    }

    override fun setData(module: Module) {
        formModule = module
        title_view.text = module.label
        setConvertView(module)
    }
}