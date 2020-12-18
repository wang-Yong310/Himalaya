package com.example.himalayaproject.ui.player

import com.ximalaya.ting.android.opensdk.model.track.Track
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode

/**
 * @author wangyong
 * @date 12/11/20 11:48 AM
 */
interface IPlayerViewCallback {
    /**
     * 开始播放
     */
    fun onPlayStart()

    /**
     * 播放暂停
     */
    fun onPlayPause()

    /**
     * 播放停止
     */
    fun onPlayStop()

    /**
     * 播放错误
     */
    fun onPlayError()

    /**
     * 下一首播放
     *
     * @param track
     */
    fun nextPlay(track: Track?)

    /**
     * 上一首播放
     *
     * @param track
     */
    fun onPrePlay(track: Track?)

    /**
     * 播放列表数据加载完成
     *
     * @param list
     */
    fun onListLoaded(list: List<Track?>?)

    /**
     * 播放器模式改变
     *
     * @param playMode
     */
    fun onPlayModeChange(playMode: PlayMode)

    /**
     * 进度条的改变
     *
     * @param currentProgress
     * @param total
     */
    fun onProgressChange(currentProgress: Int, total: Int)

    /**
     * 广告正在加载
     */
    fun onAdLoading()

    /**
     * 广告加载结束
     */
    fun onAdFinish()

    /**
     * 更新当前节目
     * @param track 节目
     */
    fun onTrackUpdate(track: Track?, playIndex: Int)


}