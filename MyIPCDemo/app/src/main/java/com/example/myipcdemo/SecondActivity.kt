package com.example.myipcdemo

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * @author wangyong
 * @date 1/8/21 7:38 PM
 */
class SecondActivity : Activity(){
    var TAG:String = "SecondActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cativity_second_test)

        Log.d(TAG, "SecondActivity onCreate")
    }
}