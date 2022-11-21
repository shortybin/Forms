package com.shortybin.form.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup

class NewLineRadioGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RadioGroup(
    context, attrs
) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (orientation == HORIZONTAL) {
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val heightSize = MeasureSpec.getSize(heightMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)

            //调用ViewGroup的方法，测量子view
            measureChildren(widthMeasureSpec, heightMeasureSpec)

            //最大的宽
            var maxWidth = 0
            //累计的高
            var totalHeight = 0

            //当前这一行的累计行宽
            var lineWidth = 0
            //当前这行的最大行高
            var maxLineHeight = 0
            //用于记录换行前的行宽和行高
            var oldHeight: Int
            var oldWidth: Int
            val count = childCount
            //假设 widthMode和heightMode都是AT_MOST
            for (i in 0 until count) {
                val child: View = getChildAt(i)
                val params = child.layoutParams as MarginLayoutParams
                //得到这一行的最高
                oldHeight = maxLineHeight
                //当前最大宽度
                oldWidth = maxWidth
                val deltaX: Int = child.getMeasuredWidth() + params.leftMargin + params.rightMargin
                if (lineWidth + deltaX + paddingLeft + paddingRight > widthSize) { //如果折行,height增加
                    //和目前最大的宽度比较,得到最宽。不能加上当前的child的宽,所以用的是oldWidth
                    maxWidth = Math.max(lineWidth, oldWidth)
                    //重置宽度
                    lineWidth = deltaX
                    //累加高度
                    totalHeight += oldHeight
                    //重置行高,当前这个View，属于下一行，因此当前最大行高为这个child的高度加上margin
                    maxLineHeight = child.measuredHeight + params.topMargin + params.bottomMargin
                } else {
                    //不换行，累加宽度
                    lineWidth += deltaX
                    //不换行，计算行最高
                    val deltaY: Int = child.measuredHeight + params.topMargin + params.bottomMargin
                    maxLineHeight = maxLineHeight.coerceAtLeast(deltaY)
                }
                if (i == count - 1) {
                    //前面没有加上下一行的搞，如果是最后一行，还要再叠加上最后一行的最高的值
                    totalHeight += maxLineHeight
                    //计算最后一行和前面的最宽的一行比较
                    maxWidth = lineWidth.coerceAtLeast(oldWidth)
                }
            }

            //加上当前容器的padding值
            maxWidth += paddingLeft + paddingRight
            totalHeight += paddingTop + paddingBottom
            setMeasuredDimension(
                if (widthMode == MeasureSpec.EXACTLY) widthSize else maxWidth,
                if (heightMode == MeasureSpec.EXACTLY) heightSize else totalHeight
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (orientation == HORIZONTAL) {
            val count = childCount
            //pre为前面所有的child的相加后的位置
            var preLeft = paddingLeft
            var preTop = paddingTop
            //记录每一行的最高值
            var maxHeight = 0
            for (i in 0 until count) {
                val child: View = getChildAt(i)
                val params = child.getLayoutParams() as MarginLayoutParams
                //r-l为当前容器的宽度。如果子view的累积宽度大于容器宽度，就换行。
                if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + paddingRight > r - l) {
                    //重置
                    preLeft = paddingLeft
                    //要选择child的height最大的作为设置
                    preTop = preTop + maxHeight
                    maxHeight =
                        getChildAt(i).measuredHeight + params.topMargin + params.bottomMargin
                } else { //不换行,计算最大高度
                    maxHeight = Math.max(
                        maxHeight,
                        child.getMeasuredHeight() + params.topMargin + params.bottomMargin
                    )
                }
                //left坐标
                val left = preLeft + params.leftMargin
                //top坐标
                val top = preTop + params.topMargin
                val right: Int = left + child.getMeasuredWidth()
                val bottom: Int = top + child.getMeasuredHeight()
                //为子view布局
                child.layout(left, top, right, bottom)
                //计算布局结束后，preLeft的值
                preLeft += params.leftMargin + child.getMeasuredWidth() + params.rightMargin
            }
        } else {
            super.onLayout(changed, l, t, r, b)
        }
    }

}