package com.example.himalayaproject.fragments;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalayaproject.R;
import com.example.himalayaproject.adapters.RecommendListAdapter;
import com.example.himalayaproject.base.BaseFragment;
import com.example.himalayaproject.interfaces.IRecommendViewCallback;
import com.example.himalayaproject.presentes.RecommendPresenter;
import com.example.himalayaproject.utils.LogUtil;
import com.example.himalayaproject.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;


public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {
    private final static String TAG = "RecommendFragment";
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecommendListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;
    private UILoader mUiLoader;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        if (mRootView == null) {
            mUiLoader = new UILoader(getContext()) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    LogUtil.d(TAG,"getSuccessView 执行了");
                    return createSuccessView(layoutInflater,container);
                }
            };
        }

        //3. 设置适配器
        mRecommendListAdapter = new RecommendListAdapter();
        mRecyclerView.setAdapter(mRecommendListAdapter);

        //去拿数据
        //  getRecommendData();

        //获取到逻辑层的对象
        mRecommendPresenter = RecommendPresenter.getInstance();
        //先要设置通知接口的注册
        mRecommendPresenter.registerViewCallBack(this);
        //获取推荐列表
        mRecommendPresenter.getRecommendList();

        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        //返回view，给界面显示
        return mRootView;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);
        LogUtil.d(TAG, "" + mRootView);
        //RecycleView的使用
        //1. 第一步找到相应的控件
        mRecyclerView = mRootView.findViewById(R.id.recommend_list);
        //2. 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置垂直方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        return mRootView;
    }


    /**
     * 获取推荐内容
     * 这个接口3.10.6
     *
     * @param result
     */
    @Override
    public void onRecommendListLoaded(List<Album> result) {
        //当我们获取到推荐内容的时候，这个方法就会调用（成功了）
        //数据回来，就更新UI
        //把数据谁个i适配去，并更新UI
        mRecommendListAdapter.setDate(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetWorkError() {
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        LogUtil.d(TAG, "onLoading");
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallBack(this);
        }
    }
}
