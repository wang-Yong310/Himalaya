package com.example.himalayaproject.presentes;

import com.example.himalayaproject.interfaces.IRecommendPresenter;
import com.example.himalayaproject.interfaces.IRecommendViewCallback;
import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.himalayaproject.utils.Constants.RECOMMEND_CONT;

public class RecommendPresenter implements IRecommendPresenter {
    private final static String TAG = "RecommendPresenter";

    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private RecommendPresenter() {

    }

    /**
     * 获得单例对象
     */
    private static RecommendPresenter sInstance = null;

    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getRecommendList() {
        //获取数据之前先加载
        updateLoading();
        //封装参数  获取推荐内容
        Map<String, String> map = new HashMap<>();
        //这个参数表示一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, String.valueOf(RECOMMEND_CONT));
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                LogUtil.d(TAG, "thread name - > " + Thread.currentThread().getName());
                //数据获取成功
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //拿到集合数据以后更新UI
                    //upRecommendUI(albumList);
                    handlerRecommendResult(albumList);

                    LogUtil.d(TAG, "List Size : " + albumList.size());
                    for (int i = 0; i < albumList.size(); i++) {
                        LogUtil.d(TAG, "猜你喜欢 : " + albumList.get(i).getAlbumTitle());
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                //数据获取出错
                LogUtil.d(TAG, "error code : " + i + "error msg : " + s);
                handlerError();
            }
        });
    }

    private void handlerError() {
        //通知UI更新
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback : mCallbacks) {
                callback.onNetWorkError();
            }
        }
    }

    private void handlerRecommendResult(List<Album> albumList) {
        if (albumList != null) {
            if (albumList.size() == 0) {
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onEmpty();
                }
            } else {
                //通知UI更新
                for (IRecommendViewCallback callback : mCallbacks) {
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }
    }
    private void updateLoading() {
        for (IRecommendViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }
    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallBack(IRecommendViewCallback callback) {
        if (!mCallbacks.contains(callback) && mCallbacks != null) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallBack(IRecommendViewCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(mCallbacks);
        }
    }

}
