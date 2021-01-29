package com.example.notificationdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

/**
 * @author wangyong
 * @date 1/9/21 5:16 PM
 * @JvmOverloads 在有默认参数值的方法中使用@JvmOverloads注解，则Kotlin就会暴露多个重载方法。
 */
class InputNumberView//
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(
        context,
        attrs,
        defStyleAttr
    ) {
    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_input_number, this, true)
        addView(view)
    }
}