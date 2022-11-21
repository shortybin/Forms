package com.shortybin.form

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import com.shortybin.form.bean.Module
import kotlinx.android.synthetic.main.form_radio_view.view.*

class FormRadioView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FormView(context, attrs) {
    val contentMap = mutableMapOf<String, String>()

    override fun layoutId(): Int {
        return R.layout.form_radio_view
    }

    override fun verify(): Boolean {
        return if (formModule.required) {
            contentMap.isNotEmpty()
        } else {
            true
        }
    }

    override fun getContent(): Map<String, String> {
        return contentMap
    }

    override fun setData(module: Module) {
        val resources = module.resource.first()
        if (resources.type == 1) {
            resources.options.forEach {
                val radioButton = RadioButton(context)
                radioButton.text = it.label
                it.event?.apply {
                    radioButton.setOnCheckedChangeListener { _, isChecked ->
                        link_module.forEach { id ->
                            val view = boxView.findViewWithTag<FormGroupView>(id)
                            if (isChecked) {
                                contentMap[module.field] = it.value
                                view.visibility = VISIBLE
                            } else {
                                view.visibility = GONE
                            }
                        }
                    }
                }
                radio_group.addView(radioButton)
            }
        } else if (resources.type == 2) {

        }
    }
}