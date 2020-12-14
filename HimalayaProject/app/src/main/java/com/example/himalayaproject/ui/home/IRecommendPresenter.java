package com.example.himalayaproject.ui.home;

import com.example.himalayaproject.base.IBasePresent;

public interface IRecommendPresenter extends IBasePresent<IRecommendViewCallback> {
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

}
