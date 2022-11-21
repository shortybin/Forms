package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import com.shortybin.form.bean.Module

abstract class FormGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormView(context, attrs) {
    lateinit var convertView: FormView

    fun typeConvertView(type: String): FormView {
        return when (type) {
            "simpleText" -> FormInputView(context)
            "radio" -> FormRadioView(context)
            "checkbox" -> FormCheckBoxView(context)
            "dropDownRadio" -> FormDropDownRadioView(context)
            "dropDownCheckBox" -> FormDropDownCheckBoxView(context)
            else -> FormInputView(context)
        }
    }

    fun setConvertView(module: Module) {
        convertView = typeConvertView(module.engine.name)
        convertView.boxView = boxView
        formView.addView(convertView)
        convertView.formModule = module
        convertView.setData(module)
    }
}