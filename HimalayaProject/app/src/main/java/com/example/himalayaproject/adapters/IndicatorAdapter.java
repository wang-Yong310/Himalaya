package com.example.himalayaproject.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.himalayaproject.MainActivity;
import com.example.himalayaproject.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class IndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mArray;
    private OnIndicatorTapClickLister mOnIndicatorTapClickLister;

    public IndicatorAdapter(Context context) {
        mArray = context.getResources().getStringArray(R.array.indicator_name);
    }

    @Override
    public int getCount() {
        if (mArray!=null) {
            return mArray.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        //创建view
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况下的颜色为灰色
        simplePagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        //设置选中情况下的颜色为黑色
        simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        //单位sp
        simplePagerTitleView.setTextSize(18);
        //设置显示内容
        simplePagerTitleView.setText(mArray[index]);
        //当我们点击时候。如果title发生了变化，下面的ViewPage也会发生变化
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制viewPager改变
                if (mOnIndicatorTapClickLister != null) {
                    mOnIndicatorTapClickLister.onTagClick(index);
                }
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(Color.parseColor("#ffffff"));
        return indicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickLister listener) {
        this.mOnIndicatorTapClickLister = listener;
    }

    public interface OnIndicatorTapClickLister {
        void onTagClick(int index);
    }
}
