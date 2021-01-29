package com.example.himalayaproject.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.himalayaproject.R
import com.example.himalayaproject.adapters.PlayListAdapter
import com.example.himalayaproject.base.BaseApplication
import com.ximalaya.ting.android.opensdk.model.track.Track
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl

/**
 * @author wangyong
 * @date 12/18/20 2:45 PM
 */
class PopWindow : PopupWindow() {
    var mPopView: View = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.pop_play_list, null, false)
    private lateinit var mCloseView: View
    private lateinit var mTracksList: RecyclerView
    private lateinit var playListAdapter: PlayListAdapter
    private lateinit var mPlayModeTv: TextView
    private lateinit var mPlayModeIv: ImageView
    private lateinit var playModeContainer: View
    private lateinit var mPlayModelClickListener: PlayListActionListener
    private lateinit var orderContainer: View
    private lateinit var mOrderIcon: ImageView
    private lateinit var mOrderText: TextView
    //载入View
    init {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        contentView = mPopView
        setWidth(width)
        setHeight(height)
        //android 5.0 以下版本设置 isOutsideTouchable 之前要设置 setBackgroundDrawable
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //设置窗口进入和退出动画
        animationStyle = R.style.pop_animation
        initView()
        initEvent()
    }

    private fun initView() {
        mCloseView = mPopView.findViewById<TextView>(R.id.tv_play_list_bottom_close)
        //找到RecycleView控件
        mTracksList = mPopView.findViewById(R.id.rv_play_list)
        //设置布局管理器
        var layoutManager = LinearLayoutManager(BaseApplication.getAppContext())
        mTracksList.layoutManager = layoutManager
        //设置适配器
        playListAdapter = PlayListAdapter()
        mTracksList.adapter = playListAdapter
        //播放模式相关
        mPlayModeTv = mPopView.findViewById(R.id.tv_play_list_mode)
        mPlayModeIv = mPopView.findViewById(R.id.iv_play_mode_switch)
        playModeContainer = mPopView.findViewById(R.id.play_list_container)
        //播放顺序相关
        orderContainer = mPopView.findViewById(R.id.play_list_order_container)
        mOrderIcon = mPopView.findViewById(R.id.iv_play_list_order)
        mOrderText = mPopView.findViewById(R.id.tv_play_list_order)
    }

    private fun initEvent() {
        //点击以后消失
        mCloseView.setOnClickListener {
            this.dismiss()
        }
        //切換播放模式
        playModeContainer.setOnClickListener {
            mPlayModelClickListener.onPlayModelClick()
        }
        //切换正序逆序播放
        orderContainer.setOnClickListener{
            mPlayModelClickListener.onOrderClick()
        }

    }

    /**
     * 给是适配器设置数据
     */
    fun setListData(data: List<Track>) {
        playListAdapter.setData(data)
    }

    fun setCurrentPlayIndex(position: Int) {
        playListAdapter.setCurrentPlayPosition(position)
        mTracksList.scrollToPosition(position)
    }

    interface PlayListItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setPlayListItemClickListener(listener: PlayListItemClickListener) {
        playListAdapter.setOnPlayItemClickListener(listener)
    }

    fun setPlayListActionListener(listener: PlayListActionListener) {
        mPlayModelClickListener = listener
    }

    /**
     * 更新播放列表的播放模式
     */
    fun updatePlayMode(mCurrentModel: XmPlayListControl.PlayMode) {
        updatePlayModeBtnImg(mCurrentModel)
    }



    interface PlayListActionListener {
        //播放模式被点击了
        fun onPlayModelClick()
        //正序逆序被点击了
        fun onOrderClick()
    }
    /**
     * 更新切换列表顺序和逆序的按钮和文字更新
     */
    fun updateOrderIcon(isOrder: Boolean) {
        mOrderIcon.setImageResource(if (isOrder) R.drawable.selector_play_mode_list_revers else R.drawable.selector_play_mode_list_order)
        mOrderText.text = BaseApplication.getAppContext().resources.getString(if (isOrder) R.string.order_text else R.string.revers_text)
    }
    /**
     * 根据当前状态，更新播放器模式图标
     */
    private fun updatePlayModeBtnImg(playMode: XmPlayListControl.PlayMode) {
        var resId: Int = R.drawable.selector_play_mode_list_order
        var testId: Int = R.string.play_mode_order_text
        when (playMode) {
            XmPlayListControl.PlayMode.PLAY_MODEL_LIST -> {
                resId = R.drawable.selector_play_mode_list_order
                testId = R.string.play_mode_order_text
            }

            XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP -> {
                resId = R.drawable.selector_play_mode_list_loop
                testId = R.string.play_mode_list_text
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM -> {
                testId = R.string.play_mode_random_text
                resId = R.drawable.selector_play_mode_list_random
            }
            XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP -> {
                testId = R.string.play_mode_single_play_text
                resId = R.drawable.selector_play_mode_list_sing_loop
            }
            else -> print("哈哈哈")
        }
        mPlayModeIv.setImageResource(resId)
        mPlayModeTv.setText(testId)
    }
}
