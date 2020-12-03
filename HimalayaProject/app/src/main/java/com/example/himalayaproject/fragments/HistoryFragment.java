package com.example.himalayaproject.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalayaproject.R;
import com.example.himalayaproject.adapters.RecommendListAdapter;
import com.example.himalayaproject.base.BaseFragment;
import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.himalayaproject.utils.Constants.RECOMMEND_CONT;

public class HistoryFragment extends BaseFragment {
    private final static String TAG = "HistoryFragment";
    private View mRootView;


    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        //view加载完成
        mRootView = layoutInflater.inflate(R.layout.fragment_history, container, false);
        return mRootView;
    }
}
