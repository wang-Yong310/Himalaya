package com.example.himalayaproject.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取到推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上接加载更多
     */
    void loadMore();

    /**
     * 这个方法用于注册UI的回调
     */
    void registerViewCallBack(IRecommendViewCallback callback);

    /**
     * 取消UI回调的注册
     * @param callback
     */
    void unRegisterViewCallBack(IRecommendViewCallback callback);
}
