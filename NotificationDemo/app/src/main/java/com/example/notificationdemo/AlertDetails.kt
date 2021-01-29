package com.example.notificationdemo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class AlertDetails: AppCompatActivity() {
    private var TAG: String = "AlertDetail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Log.d(TAG,"AlertDetails is onCreate...")
    }
}
