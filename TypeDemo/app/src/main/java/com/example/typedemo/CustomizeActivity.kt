package com.example.typedemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_customize.*

/**
 * @author wangyong
 * @date 1/18/21 7:40 PM
 */
class CustomizeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize)
        initEvent()
    }

    private fun initEvent() {
        btn_input_number?.setOnClickListener{
            val intent = Intent(this, InputNumber::class.java)
            startActivity(intent)
        }
    }
}