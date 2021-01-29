package com.example.notificationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationdemo.bean.ItemBean
import com.example.notificationdemo.demo1.NotificationAdapter
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.item.*

class NotificationActivity : AppCompatActivity() , NotificationAdapter.ItemClickListener {

    private var itemList: Array<Int> = Array(100) { k ->
        k + 1
    }

    private var mAdapter: NotificationAdapter = NotificationAdapter(itemList)
    var item: NotificationAdapter.ItemClickListener ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        var layout: LinearLayoutManager = LinearLayoutManager(this)
        rv_notification_two.layoutManager = layout
        //设置adapt
        rv_notification_two.adapter = mAdapter
        initEvent()
    }

    private fun initEvent() {
        btn.setOnClickListener{
            item?.onItemClick()
        }
    }

    override fun onItemClick() {

    }

}

