package com.example.himalayaproject.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author wangyong
 * @date 12/20/20 6:37 PM
 */
class PlayListAdapter : RecyclerView.Adapter<PlayListAdapter.InnerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return null
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {}
    override fun getItemCount(): Int {
        return 0
    }

    inner class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}