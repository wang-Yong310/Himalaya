package com.example.himalayaproject.ui.detail;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/**
 * @author wangyong
 * @date 12/7/20 3:06 PM
 */
public interface IAlbumDetailViewCallback {
    /**
     * 专辑详情内容加载
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 把album传给UI
     * @param album
     */
    void onAlbumLoaded(Album album);

    /**
     * 网络错误
     */
    void onNetWorkError(int errorCode, String errorMsg);
}
