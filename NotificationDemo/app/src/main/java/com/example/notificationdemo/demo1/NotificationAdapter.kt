package com.example.notificationdemo.demo1

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationdemo.AlertDetails
import com.example.notificationdemo.R
import com.example.notificationdemo.bean.ItemBean

/**
 * @author wangyong
 * @date 1/10/21 3:32 PM
 */
class NotificationAdapter constructor(list: Array<Int>) :
    RecyclerView.Adapter<NotificationAdapter.InnerHolder>() {
    var i = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        var itemView: View =
            View.inflate(parent.context, R.layout.item, null)
        return InnerHolder(itemView)
    }


    override fun getItemCount(): Int {
        return 100
    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView.findViewById(R.id.tv)
        var btn: Button = itemView.findViewById(R.id.btn)
        fun setData(itemBean: Int) {
            tv.text = "这是第$itemBean" + "数据"
        }


    }


    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        //设置数据
        holder.setData(i[position])
        holder.btn.setOnClickListener{

        }
    }

    interface ItemClickListener {
        fun onItemClick()
    }
}