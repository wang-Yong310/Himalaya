package com.example.himalayaproject.ui.player;


import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * @author wangyong
 * @date 12/11/20 11:48 AM
 */
public interface IPlayerViewCallback {
    /**
     * 开始播放
     */
    void onPlayStart();

    /**
     * 播放暂停
     */
    void onPlayPause();

    /**
     * 播放停止
     */
    void onPlayStop();

    /**
     * 播放错误
     */
    void onPlayError();

    /**
     * 下一首播放
     *
     * @param track
     */
    void nextPlay(Track track);

    /**
     * 上一首播放
     *
     * @param track
     */
    void onPrePlay(Track track);

    /**
     * 播放列表数据加载完成
     *
     * @param list
     */
    void onListLoaded(List<Track> list);

    /**
     * 播放器模式改变
     *
     * @param playMode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode playMode);

    /**
     * 进度条的改变
     *
     * @param currentProgress
     * @param total
     */
    void onProgressChange(long currentProgress, long total);

    /**
     * 广告正在加载
     */
    void onAdLoading();

    /**
     * 广告加载结束
     */
    void onAdFinish();
}
