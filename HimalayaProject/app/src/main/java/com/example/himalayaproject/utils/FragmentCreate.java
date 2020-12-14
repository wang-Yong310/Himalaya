package com.example.himalayaproject.utils;

import com.example.himalayaproject.base.BaseFragment;
import com.example.himalayaproject.fragments.HistoryFragment;
import com.example.himalayaproject.ui.home.RecommendFragment;
import com.example.himalayaproject.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 作用有两个1、创建fragment
 *          2、用作缓存已经创建过的fragment
 *
 */
public class FragmentCreate {
    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;
    //创建集合用来缓存
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }
        switch (index) {
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
            default:
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
