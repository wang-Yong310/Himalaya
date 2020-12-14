package com.example.himalayaproject.ui.detail;

/**
 * @author wangyong
 * @date 12/7/20 10:04 AM
 */
public interface IAlbumDetailPresenter {
    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上接加载更多
     */
    void loadMore();

    /**
     * 获取专辑详情
     * @param albumId
     * @param page
     */
    void getAlbumDetail(int albumId, int page);


    /**
     * 注册UI通知接口
     * @param detailPresenter
     */
    void registerViewCallback(IAlbumDetailViewCallback detailPresenter);

    /**
     * 取消注册UI通知
     * @param detailPresenter
     */
    void unRegisterViewCallback(IAlbumDetailViewCallback detailPresenter);

}
