package com.example.himalayaproject.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.himalayaproject.R;
import com.example.himalayaproject.base.BaseApplication;
import com.example.himalayaproject.utils.LogUtil;

/**
 * @author wangyong
 * @date 12/3/20 11:46 AM
 */
public abstract class UILoader extends FrameLayout {

    private static final String TAG = "UILoader";
    private View mLoadingView;
    private View mSuccessView;
    private View mNetworkErrorView;
    private View mEmptyView;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        init();
    }


    public void updateStatus(UIStatus status) {
        //UILoader.UIStatus.LOADING
        LogUtil.d(TAG, "status-->" + status);
        mCurrentStatus = status;
        //更新UI一定要在主线程上
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {

        //加载中
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
            LogUtil.d(TAG,"加载完成");
        }
        //根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

        //加载成功
        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            LogUtil.d(TAG,"加载成功");
            addView(mSuccessView);
        }
        //根据状态设置是否可见
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);


        //网络错误页面
        if (mNetworkErrorView == null) {
            mNetworkErrorView = getNetworkErrorView();
            addView(mNetworkErrorView);
            LogUtil.d(TAG,"网络错误--");
        }
        //根据状态设置是否可见
        mNetworkErrorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        //数据为空的页面
        if (mEmptyView== null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
            LogUtil.d(TAG,"数据为空");
        }
        //根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getNetworkErrorView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
    }

    protected abstract View getSuccessView(ViewGroup container);

    protected View getLoadingView() {
        LogUtil.d(TAG,"获取loading 页面");
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }
}
