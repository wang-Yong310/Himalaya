package com.example.himalayaproject.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.viewpager.widget.ViewPager
import com.example.himalayaproject.R
import com.example.himalayaproject.base.BaseActivity
import com.example.himalayaproject.base.BasePageTransformer
import com.example.himalayaproject.utils.LogUtil
import com.example.himalayaproject.views.PopWindow
import com.ximalaya.ting.android.opensdk.model.track.Track
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode
import kotlinx.android.synthetic.main.activity_player.*
import java.text.SimpleDateFormat


/**
 * @author wangyong
 * @date 12/10/20 4:42 PM
 */
class PlayerActivity : BaseActivity(), IPlayerViewCallback, ViewPager.OnPageChangeListener, BasePageTransformer {
    private val TAG: String = "PlayerActivity"
    private var mMinFormat: SimpleDateFormat = SimpleDateFormat("mm:ss")
    private var mHourFormat: SimpleDateFormat = SimpleDateFormat("hh:mm:ss")

    //    private var mPlayerPresenter: PlayerPresenter? = null
    lateinit var mPlayerPresenter: PlayerPresenter
    private var mCurrentProgress: Int = 0
    private var mIsUseTouchProgressBar: Boolean = false
    private var mMaxRotation: Float = 90.0f

    //创建适配器
    private var mTrackPagerAdapter: PlayerTrackPagerAdapter? = PlayerTrackPagerAdapter()

    //当前的播放模式
    private var mCurrentModel: PlayMode = PlayMode.PLAY_MODEL_LIST

    //1. 默认是
    //   列表播放  PLAY_MODEL_LIST
    //2. 列表循环  PLAY_MODEL_LIST_LOOP
    //3. 随机播放  PLAY_MODEL_RANDOM
    //4. 单曲循环  PLAY_MODEL_SINGLE_LOOP
    private val playList: PlayMode = PlayMode.PLAY_MODEL_LIST
    private val playListLoop: PlayMode = PlayMode.PLAY_MODEL_LIST_LOOP
    private val playListRandom: PlayMode = PlayMode.PLAY_MODEL_RANDOM
    private val playSingleLoop: PlayMode = PlayMode.PLAY_MODEL_SINGLE_LOOP
    private val sPlayModelRule = mapOf(playList to playListLoop, playListLoop to playListRandom, playListRandom to playSingleLoop, playSingleLoop to playList)
    private val mPopWindow: PopWindow = PopWindow()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        mPlayerPresenter = PlayerPresenter.getPlayerPresenter()
        mPlayerPresenter.registerViewCallback(this)
        //设置适配器
        vp_pager_view?.adapter = mTrackPagerAdapter
        //在界面初始化以后在获得列表
        mPlayerPresenter.getPlayList()
        initEvent()
        vp_pager_view.setPageTransformer(true, PlayerActivity())
    }


    /**
     * 给控件设置相关的事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {
        iv_play_or_pause?.setOnClickListener {
            //如果现在的状态是正在播放的，那么就暂停
            if (mPlayerPresenter.isPlay) {
                mPlayerPresenter.pause()
            } else {
                //如果是非播放的，那就让播放器播放
                mPlayerPresenter.play()
            }
        }

        sb_track!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, isFromUser: Boolean) {
                if (isFromUser) {
                    mCurrentProgress = progress
                }
                LogUtil.d(TAG, "isFromUser-->$isFromUser")
            }

            /**
             * 触摸调用方法
             */
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mIsUseTouchProgressBar = true
            }

            /**
             * 手离开拖动进度条时候更新进度调用方法
             */
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mIsUseTouchProgressBar = false
                mPlayerPresenter.seekTo(mCurrentProgress)
            }
        })

        iv_play_pre.setOnClickListener {
            //播放上一个节目
            mPlayerPresenter.playPre()

        }

        iv_play_next.setOnClickListener {
            //播放下一个节目
            mPlayerPresenter.playNext()
        }
        //屏幕中图片监听事件
        vp_pager_view.addOnPageChangeListener(this)

        vp_pager_view.setOnTouchListener { v, event ->
            var action: Int? = event.action
            when (action) {
                MotionEvent.ACTION_DOWN ->
                    mIsUseTouchProgressBar = true
            }
            return@setOnTouchListener false
        }
        //设置iv_player_node_switch点击事件，播放模式的切换
        iv_player_node_switch.setOnClickListener {
            //根据当前的model获取到下一个model
            val playMode = sPlayModelRule[mCurrentModel]
            LogUtil.d(TAG, "playMode--> $playMode")
            //修改播放模式
            mPlayerPresenter.switchPlayMode(playMode)
            updatePlayModeBtnImg()
        }
        //设置播放列表的点击事件
        iv_play_list.setOnClickListener { view ->
            LogUtil.d(TAG,"iv_play_list--> 点击了")
            mPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        }
    }

    /**
     * 根据当前状态，更新播放器模式图标
     */
    private fun updatePlayModeBtnImg() {
        var resId: Int = R.drawable.selector_play_mode_list_order
        when (mCurrentModel) {
            playList -> resId = R.drawable.selector_play_mode_list_order
            playListLoop -> resId = R.drawable.selector_play_mode_list_loop
            playListRandom -> resId = R.drawable.selector_play_mode_list_random
            playSingleLoop -> resId = R.drawable.selector_play_mode_list_sing_loop
            else -> print("哈哈哈")
        }
        iv_player_node_switch.setImageResource(resId)
    }


    override fun onPlayStart() {
        //开始播放修改UI暂停的按钮
        //判空是因为回调可能比控件初始化早
        iv_play_or_pause?.setImageResource(R.drawable.selector_play_stop)
    }

    override fun onPlayPause() {
        //暂停播放修改UI的按钮
        iv_play_or_pause?.setImageResource(R.drawable.selector_play_or_pause)
    }

    override fun onPlayStop() {
        //停止时的Ui按钮.
        iv_play_or_pause?.setImageResource(R.drawable.selector_play_or_pause)
    }

    override fun onPlayError() {}
    override fun nextPlay(track: Track?) {}
    override fun onPrePlay(track: Track?) {}

    override fun onPlayModeChange(playMode: PlayMode) {
        //更新模式并修UI
        mCurrentModel = playMode
        updatePlayModeBtnImg()
    }

    override fun onProgressChange(currentProgress: Int, total: Int) {
        sb_track.max = total
        //更新播放进度，更新进度条
        var totalDuration: String? = null
        var currentPosition: String? = null
        if (total > 1000 * 60 * 60) {
            totalDuration = mHourFormat.format(total)
            currentPosition = mHourFormat.format(currentProgress)
        } else {
            totalDuration = mMinFormat.format(total)
            currentPosition = mMinFormat.format(currentProgress)
        }
        tv_current_duration?.text = totalDuration
        //更新进度
        tv_current_position?.text = currentPosition
        //更新当前的时间
        //计算当前进度
        if (!mIsUseTouchProgressBar) {
            LogUtil.d(TAG, "percent -->$currentProgress")
            sb_track.progress = currentProgress
        }

    }

    override fun onAdLoading() {

    }

    override fun onAdFinish() {}
    override fun onTrackUpdate(track: Track?, playIndex: Int) {
        //设置当前结果的标题
        this.tv_player_title?.text = track?.trackTitle
        //当节目改变时候，就获取当前播放器中播放位置
        //当前的节目修改以后要修改页面的图片
        this.vp_pager_view?.setCurrentItem(playIndex, true)
    }


    override fun onDestroy() {
        mPlayerPresenter.unRegisterViewCallback(this)
        super.onDestroy()
    }

    override fun onListLoaded(list: List<Track?>?) {
        LogUtil.d(TAG, "list --> $list")
        //把数据设置到适配器里
        mTrackPagerAdapter?.setData(list)
    }

    /**
     * 这个方法在屏幕滚动时候不断被调用
     * @param position 当用手指滑动时
     * 如果手指按在页面上不动，position和当前页面index是一致的
     * 如果手指向左拖动（相应页面向右翻动），这时候position大部分时间和当前页面是一致的，只有翻页成功的情况下最后一次调用才会变为目标页面
     * 如果手指向右拖动（相应页面向左翻动），这时候position大部分时间和目标页面是一致的，只有翻页不成功的情况下最后一次调用才会变为原页面
     * @param positionOffset 当前页面因滑动偏移了多少比例
     * @param positionOffsetPixels 当前页面偏移了多少像素
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        LogUtil.d(TAG, "positionOffsetPixels-->$positionOffsetPixels")
    }

    /**
     * 手指抬起的时候调用
     * 当页面选中的时候，就去切换播放的内容
     * "?"加在变量名后，系统在任何情况不会报它的空指针异常。
     * "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
     */
    override fun onPageSelected(position: Int) {
        LogUtil.d(TAG, "mIsUseTouchProgressBar--> $mIsUseTouchProgressBar")
        when (mIsUseTouchProgressBar) {
            true -> mPlayerPresenter.playByIndex(position)
        }
        mIsUseTouchProgressBar = false

    }

    /**
     * 手指滑动一次这个方法执行三次手指滑动
     *（滑动时）手指按下去的时候会触发这个方法，state值为1
     * 手指抬起时，如果发生了滑动（即使很小），就会触发这个方法，这个值会变为2
     * 最后滑动结束，页面停止的时候，也会触发这个方法，值变为0
     * 所以一次手指滑动会执行这个方法三次
     * 一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方法两次，state值分别是1,0
     */
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun touch2Left(view: View, position: Float) {
        //设置旋转中心点在当前界面的右侧
        view.pivotX = view.measuredWidth.toFloat()
        view.pivotY = view.measuredHeight * 0.5f
        view.rotationY = mMaxRotation * position
    }

    override fun touch2Right(view: View, position: Float) {
        //设置旋转中心点在当前界面的左侧
        view.pivotX = 0.0f
        view.pivotY = view.measuredHeight * 0.5f
        view.rotationY = mMaxRotation * position
        //动态改变旋转角度
    }


}




