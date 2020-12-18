package com.example.himalayaproject.ui.player;

import com.example.himalayaproject.base.IBasePresent;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

/**
 * @author wangyong
 * @date 12/11/20 11:37 AM
 */
public interface IPlayerPresenter extends IBasePresent<IPlayerViewCallback> {
    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 上一首
     */
    void playPre();

    /**
     * 下一首
     */
    void playNext();

    /**
     * 切换播放模式
     * @param mode
     */
    void switchPlayMode(XmPlayListControl.PlayMode mode);

    /**
     * 获取播放列表
     */
    void getPlayList();

    /**
     * 根据节目位置进行播放
     * @param index  在列表中的位置
     */
    void playByIndex(int index);

    /**
     * 切换播放进度
     *
     * @param progress
     */
    void seekTo(int progress);

    /**
     * 判断播放器是否在播放
     * @return
     */
    boolean isPlay();

}
