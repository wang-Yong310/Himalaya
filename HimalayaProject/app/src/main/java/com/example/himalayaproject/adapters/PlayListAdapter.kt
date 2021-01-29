package com.example.himalayaproject.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.himalayaproject.R
import com.example.himalayaproject.base.BaseApplication
import com.example.himalayaproject.views.PopWindow
import com.ximalaya.ting.android.opensdk.model.track.Track

/**
 * @author wangyong
 * @date 12/20/20 6:37 PM
 */
class PlayListAdapter : RecyclerView.Adapter<PlayListAdapter.InnerHolder>() {
    private lateinit var mItemClickListener: PopWindow.PlayListItemClickListener
    private var mData: ArrayList<Track> = ArrayList()
    private var playingIndex: Int = 0
    private var colorOrange = ContextCompat.getColor(BaseApplication.getAppContext(), R.color.main_color)
    private var colorGray = ContextCompat.getColor(BaseApplication.getAppContext(), R.color.playlist_list_text_color)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_play_list, parent, false)
        return InnerHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        //holder点击了
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(position)
        }
        //设置数据
        val track = mData[position]
        val trackTitleTv: TextView = holder.itemView.findViewById(R.id.tv_track_title)
        trackTitleTv.text = track.trackTitle
        if (position == playingIndex) trackTitleTv.setTextColor(colorOrange) else trackTitleTv.setTextColor(colorGray)
        val trackIconIv: ImageView = holder.itemView.findViewById(R.id.iv_track_icon)
        trackIconIv.visibility = if (playingIndex == position) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setData(data: List<Track>) {
        //设置数据,更新列表
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setCurrentPlayPosition(position: Int) {
        playingIndex = position
        notifyDataSetChanged()
    }

    fun setOnPlayItemClickListener(listener: PopWindow.PlayListItemClickListener) {
        this.mItemClickListener = listener
    }


    inner class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}

