package com.example.himalayaproject.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.himalayaproject.utils.FragmentCreate;
import com.example.himalayaproject.utils.LogUtil;

public class MainContentAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainContentAdapter";

    public MainContentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * 适配器显示Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        LogUtil.d(TAG, "p : " + position);
        //创建Fragment，把位置传进去
        return FragmentCreate.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentCreate.PAGE_COUNT;
    }
}
