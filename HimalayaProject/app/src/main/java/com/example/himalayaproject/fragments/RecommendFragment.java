package com.example.himalayaproject.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.himalayaproject.R;
import com.example.himalayaproject.base.BaseFragment;

public class RecommendFragment extends BaseFragment {

    private View mRootView;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);
        return mRootView;
    }
}
