package com.example.himalayaproject.ui.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.himalayaproject.R;
import com.example.himalayaproject.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyong
 * @date 12/15/20 9:41 AM
 */
public class PlayerTrackPagerAdapter extends PagerAdapter {
    private List<Track> mDate = new ArrayList<>();
    private static final String TAG = "PlayerTrackPagerAdapter";

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_pager, container, false);
        container.addView(itemView);
        //设置数据
        //找到控件
        ImageView item = itemView.findViewById(R.id.iv_track_pager_item);
        //设置图片
        Track track = mDate.get(position);
        String coverUrlLarge = track.getCoverUrlLarge();
        Picasso.with(container.getContext()).load(coverUrlLarge).into(item);
        LogUtil.d(TAG, "image -->" + coverUrlLarge);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<Track> list) {
        mDate.clear();
        mDate.addAll(list);
        notifyDataSetChanged();
    }
}
