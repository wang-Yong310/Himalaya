package com.example.himalayaproject.ui.player;

import com.example.himalayaproject.base.BaseApplication;
import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

/**
 * @author wangyong
 * @date 12/11/20 4:39 PM
 */

/**
 * @author wangyong
 * @date 12/11/20 2:32 PM
 */
public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener {

    private static final String TAG = "PlayerPresenter";

    private final XmPlayerManager mPlayerManager;

    private PlayerPresenter() {
        mPlayerManager =  XmPlayerManager.getInstance(BaseApplication.getAppContext());

        //广告物料
        mPlayerManager.addAdsStatusListener(this);
    }

    private static PlayerPresenter sPlayerPresenter;
    public static PlayerPresenter getPlayerPresenter() {
        if (sPlayerPresenter == null) {
            synchronized (PlayerPresenter.class) {
                if (sPlayerPresenter == null) {
                    sPlayerPresenter = new PlayerPresenter();
                }
            }
        }
        return sPlayerPresenter;
    }

    private boolean isPlayListSet = false;
    /**
     * 设置播放器列表
     * @param list
     * @param playIndex
     */
    public void setPlayList(List<Track> list, int playIndex) {
        if (mPlayerManager!=null) {
            mPlayerManager.setPlayList(list, playIndex);
        } else {
            LogUtil.d(TAG,"mPlayerManager is null");
        }
    }

    @Override
    public void play() {
        if (!isPlayListSet) {
            mPlayerManager.play();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void playPre() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public void registerViewCallback(IPlayerViewCallback iPlayerViewCallback) {

    }

    @Override
    public void unRegisterViewCallback(IPlayerViewCallback iPlayerViewCallback) {

    }

    /**
     * ========================广告方法的回调 start ======================================
     */
    @Override
    public void onStartGetAdsInfo() {
        LogUtil.d(TAG, "onStartGetAdsInfo...");
    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {
        LogUtil.d(TAG,"onGetAdsInfo...");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.d(TAG,"onAdsStartBuffering...开始缓冲");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.d(TAG,"onAdsStopBuffering...停止缓冲");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.d(TAG,"开始播放广告...onStartPlayAds");
    }

    @Override
    public void onCompletePlayAds() {

    }

    @Override
    public void onError(int i, int i1) {

    }
    /**
     * ========================广告方法的回调 start ======================================
     */

}

