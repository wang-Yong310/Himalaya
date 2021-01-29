package com.example.typedemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class InputNumber : AppCompatActivity() {
    private val TAG: String = "InputNumber"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        var argb = getARGB("rgba(255, 255, 255, 0.5)")
        if (argb != null) {
            for (i in argb) {
                Log.d(TAG, "int$argb")

            }
        }
    }
    // 过滤颜色
    open fun getARGB(buttonColor: String): IntArray? {
        var buttonColor = buttonColor
        val nums = IntArray(4)

        Log.d(TAG, "buttonColor$buttonColor")
        return nums
    }
}
