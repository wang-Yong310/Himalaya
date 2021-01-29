package com.example.himalayaproject.ui.player;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.himalayaproject.base.BaseApplication;
import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.constants.PlayerConstants;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_RANDOM;
import static com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl.PlayMode.PLAY_MODEL_SINGLE_LOOP;

/**
 * @author wangyong
 * @date 12/11/20 4:39 PM
 */

/**
 * @author wangyong
 * @date 12/11/20 2:32 PM
 */
public class PlayerPresenter implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {
    private List<IPlayerViewCallback> mIPlayerViewCallbacks = new ArrayList<>();

    private static final String TAG = "PlayerPresenter";

    private final XmPlayerManager mPlayerManager;
    private Track mCurrentTrack;
    private int mCurrentIndex = 0;
    private final SharedPreferences mPlayMode;

    private static final int PLAY_MODEL_LIST_INT = 0;
    private static final int PLAY_MODEL_LIST_LOOP_INT = 1;
    private static final int PLAY_MODEL_RANDOM_INT = 2;
    private static final int PLAY_MODEL_SINGLE_LOOP_INT = 3;
    //sp's key and name
    public static final String PLAY_MODE_SP_NAME = "PlayMod";
    public static final String PLAY_MODE_SP_KEY = "currentPlayMod";
    private XmPlayListControl.PlayMode mCurrentPlayModel = XmPlayListControl.PlayMode.PLAY_MODEL_LIST;
    private boolean mIsReverse = false;

    private PlayerPresenter() {
        mPlayerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());

        //广告物料相关的接口
        mPlayerManager.addAdsStatusListener(this);
        //注册播放器状态相关的接口
        mPlayerManager.addPlayerStatusListener(this);
        //需要记录当前播放模式
        mPlayMode = BaseApplication.getAppContext().getSharedPreferences("PlayMode", Context.MODE_PRIVATE);

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
     *
     * @param list
     * @param playIndex
     */
    public void setPlayList(List<Track> list, int playIndex) {
        if (mPlayerManager != null) {
            mPlayerManager.setPlayList(list, playIndex);
            isPlayListSet = true;
            mCurrentTrack = list.get(playIndex);
            mCurrentIndex = playIndex;
        } else {
            LogUtil.d(TAG, "mPlayerManager is null");
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
        if (mPlayerManager != null) {
            mPlayerManager.pause();
        }
    }

    @Override
    public void playPre() {
        //播放上一个节目
        if (mPlayerManager != null) {
            mPlayerManager.playPre();
        }
    }

    @Override
    public void playNext() {
        //播放下一个节目
        if (mPlayerManager != null) {
            mPlayerManager.playNext();
        }
    }
    //判断是否有播放列表的节目列表
    public boolean havePlayList() {
        return isPlayListSet;
    }

    @Override
    public void switchPlayMode(XmPlayListControl.PlayMode mode) {
        if (mPlayerManager != null) {
            mCurrentPlayModel = mode;
            mPlayerManager.setPlayMode(mode);
            //通知UI更新播放模式
            for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
                iPlayerViewCallback.onPlayModeChange(mode);
            }
            //保存到sp里面去
            SharedPreferences.Editor edit = mPlayMode.edit();
            edit.putInt(PLAY_MODE_SP_KEY, getInByPlayMode(mode));
            edit.commit();
        }
    }

    private int getInByPlayMode(XmPlayListControl.PlayMode mode) {
        switch (mode) {
            case PLAY_MODEL_SINGLE_LOOP:
                return PLAY_MODEL_SINGLE_LOOP_INT;
            case PLAY_MODEL_LIST_LOOP:
                return PLAY_MODEL_LIST_LOOP_INT;
            case PLAY_MODEL_RANDOM:
                return PLAY_MODEL_RANDOM_INT;
            case PLAY_MODEL_LIST:
                return PLAY_MODEL_LIST_INT;
        }
        return PLAY_MODEL_LIST_INT;
    }

    private XmPlayListControl.PlayMode getModelByInt(int currentIndex) {
        switch (currentIndex) {
            case PLAY_MODEL_SINGLE_LOOP_INT:
                return PLAY_MODEL_SINGLE_LOOP;
            case PLAY_MODEL_LIST_LOOP_INT:
                return PLAY_MODEL_LIST_LOOP;
            case PLAY_MODEL_RANDOM_INT:
                return PLAY_MODEL_RANDOM;
            case PLAY_MODEL_LIST_INT:
                return PLAY_MODEL_LIST;
        }
        return PLAY_MODEL_LIST;
    }

    @Override
    public void getPlayList() {
        if (mPlayerManager != null) {
            List<Track> playList = mPlayerManager.getPlayList();
            for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
                iPlayerViewCallback.onListLoaded(playList);
            }
        }
    }

    /**
     * 切换播放器播到第 index 的位置
     *
     * @param index 在列表中的位置
     */
    @Override
    public void playByIndex(int index) {
        if (mPlayerManager != null) {
            mPlayerManager.play(index);
        }
    }

    /**
     * 更新播放器的进度
     *
     * @param progress
     */
    @Override
    public void seekTo(int progress) {
        mPlayerManager.seekTo(progress);
    }

    @Override
    public boolean isPlaying() {
        //返回当前是否正在播放
        return mPlayerManager.isPlaying();
    }

    @Override
    public void reversePlayList() {
        //把播放列表翻转
        List<Track> playList = mPlayerManager.getPlayList();
        Collections.reverse(playList);
        mIsReverse = !mIsReverse;
        //第一个参数是播放列表，第二个参数是开始播放的下标
        mCurrentIndex = playList.size() - 1 - mCurrentIndex;
        mPlayerManager.setPlayList(playList, mCurrentIndex);
        //更新UI
        mCurrentTrack = (Track) mPlayerManager.getCurrSound();
        for (IPlayerViewCallback iPlayerCallback : mIPlayerViewCallbacks) {
            iPlayerCallback.onListLoaded(playList);
            iPlayerCallback.onTrackUpdate(mCurrentTrack, mCurrentIndex);
            iPlayerCallback.updateListOrder(mIsReverse);
        }
    }

    @Override
    public void registerViewCallback(IPlayerViewCallback iPlayerViewCallback) {
        iPlayerViewCallback.onTrackUpdate(mCurrentTrack, mCurrentIndex);
        //从sp里拿
        int modelIndex = mPlayMode.getInt(PLAY_MODE_SP_KEY, PLAY_MODEL_LIST_INT);
        mCurrentPlayModel = getModelByInt(modelIndex);
        //
        iPlayerViewCallback.onPlayModeChange(mCurrentPlayModel);
        if (!mIPlayerViewCallbacks.contains(iPlayerViewCallback)) {
            mIPlayerViewCallbacks.add(iPlayerViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IPlayerViewCallback iPlayerViewCallback) {
        mIPlayerViewCallbacks.add(iPlayerViewCallback);
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
        LogUtil.d(TAG, "onGetAdsInfo...");
    }

    @Override
    public void onAdsStartBuffering() {
        LogUtil.d(TAG, "onAdsStartBuffering...开始缓冲");
    }

    @Override
    public void onAdsStopBuffering() {
        LogUtil.d(TAG, "onAdsStopBuffering...停止缓冲");
    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {
        LogUtil.d(TAG, "开始播放广告...onStartPlayAds");
    }

    @Override
    public void onCompletePlayAds() {
        LogUtil.d(TAG, "播放完成了...onCompletePlayAds");
    }

    @Override
    public void onError(int what, int extra) {
        LogUtil.d(TAG, "onError what => " + what + " onError => " + extra);
    }

    /**
     * ========================广告方法的回调 end ======================================
     */
    /**
     * ========================播放器方法的回调 start ===================================
     */
    @Override
    public void onPlayStart() {
        LogUtil.d(TAG, "onPlayStart");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
            iPlayerViewCallback.onPlayStart();
        }
    }

    @Override
    public void onPlayPause() {
        LogUtil.d(TAG, "onPlayPause");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
            iPlayerViewCallback.onPlayPause();
        }
    }

    @Override
    public void onPlayStop() {
        LogUtil.d(TAG, "onPlayStop");
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
            iPlayerViewCallback.onPlayStop();
        }
    }

    @Override
    public void onSoundPlayComplete() {
        LogUtil.d(TAG, "onSoundPlayComplete");
    }

    @Override
    public void onSoundPrepared() {
        LogUtil.d(TAG, "current status  --> " + mPlayerManager.getPlayerStatus());
        mPlayerManager.setPlayMode(mCurrentPlayModel);
        if (mPlayerManager.getPlayerStatus() == PlayerConstants.STATE_PREPARED) {
            //播放器准备好可以播放
            mPlayerManager.play();
        }
    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
        LogUtil.d(TAG, "onSoundSwitch");
        if (lastModel != null) {
            LogUtil.d(TAG, "lastModel --> " + lastModel.getKind());
        }
        LogUtil.d(TAG, "curModel --> " + curModel.getKind());
        //curModel代表的是当前播放的内容
        //如果通过getKind()获取什么类型
        //第一场写法
        //if ("track".equals(curModel.getKind())) {
        //    Track currentTrack = (Track) curModel;
        //    LogUtil.d(TAG, "title=> " + currentTrack.getTrackTitle());
        //}
        mCurrentIndex = mPlayerManager.getCurrentIndex();
        if (curModel instanceof Track) {
            Track currentTrack = (Track) curModel;
            mCurrentTrack = currentTrack;

            LogUtil.d(TAG, "title -- > " + currentTrack.getTrackTitle());
            //更新UI
            for (IPlayerViewCallback iPlayerView : mIPlayerViewCallbacks) {
                iPlayerView.onTrackUpdate(mCurrentTrack, mCurrentIndex);
            }

        }


    }

    @Override
    public void onBufferingStart() {
        LogUtil.d(TAG, "onBufferingStart");
    }

    @Override
    public void onBufferingStop() {
        LogUtil.d(TAG, "onBufferingStop");
    }

    @Override
    public void onBufferProgress(int i) {
        LogUtil.d(TAG, "onBufferProgress缓冲进度--" + i);
    }

    /**
     * 播放进度
     *
     * @param curPos   当前位置 单位毫秒
     * @param duration 总工时间 单位毫秒
     */
    @Override
    public void onPlayProgress(int curPos, int duration) {
        LogUtil.d(TAG, "curPos --- " + curPos + " duration -->" + duration);
        for (IPlayerViewCallback iPlayerViewCallback : mIPlayerViewCallbacks) {
            iPlayerViewCallback.onProgressChange(curPos, duration);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        LogUtil.d(TAG, "onError => " + e);
        return false;
    }
    /**
     * ========================播放器方法的回调 end ===================================
     */
}

