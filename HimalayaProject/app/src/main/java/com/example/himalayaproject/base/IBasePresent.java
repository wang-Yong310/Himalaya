package com.example.himalayaproject.base;

/**
 * @author wangyong
 * @date 12/11/20 11:38 AM
 */
public interface IBasePresent<T> {

    /**
     * 注册UI通知接口
     *
     * @param t
     */
    void registerViewCallback(T t);

    /**
     * 取消注册UI通知
     *
     * @param t
     */
    void unRegisterViewCallback(T t);
}
