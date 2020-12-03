package com.example.himalayaproject;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.example.himalayaproject.adapters.IndicatorAdapter;
import com.example.himalayaproject.adapters.MainContentAdapter;
import com.example.himalayaproject.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;


public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private MagicIndicator mMagicIndicator;
    private IndicatorAdapter mIndicatorAdapter;
    private CommonNavigator mCommonNavigator;
    private ViewPager mContentPager;
    private FragmentManager mSupportFragmentManager;
    private MainContentAdapter mMainContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEven();
    }

    /**
     * 初始化滑动
     */
    private void initEven() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickLister() {
            @Override
            public void onTagClick(int index) {
                LogUtil.d(TAG, "onTagClick index : " + index);
                if (mContentPager != null) {
                    mContentPager.setCurrentItem(index);
                }
            }
        });
    }


    /**
     * 初始化控件
     */
    private void initView() {
        mMagicIndicator = this.findViewById(R.id.main_indicator);
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));
        //创建IndicatorAdapter适配器
        mIndicatorAdapter = new IndicatorAdapter(this);
        mCommonNavigator = new CommonNavigator(this);
        //平均分
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(mIndicatorAdapter);
       // //设置要显示的内容


        //viewPager
        mContentPager = findViewById(R.id.content_pager);

        //创建内容适配器
        mSupportFragmentManager = getSupportFragmentManager();
        mMainContentAdapter = new MainContentAdapter(mSupportFragmentManager);

        mContentPager.setAdapter(mMainContentAdapter);

        //把ViewPager和indicator绑定在一起
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mContentPager);
    }
}