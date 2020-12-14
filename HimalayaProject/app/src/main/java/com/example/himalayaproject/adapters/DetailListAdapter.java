package com.example.himalayaproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalayaproject.R;
import com.example.himalayaproject.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyong
 * @date 12/9/20 10:58 AM
 */
public class DetailListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private static final String TAG = "DetailListAdapter";
    private List<Track> mDetailData = new ArrayList<>();
    //格式化时间
    private DateTimeFormatter mDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat mUpdateDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    private ItemClickListener mItemClickListener = null;


    @NonNull
    @Override
    public RecommendListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail, parent, false);
        return new RecommendListAdapter.InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendListAdapter.InnerHolder holder, int position) {
        //找到控件，设置数据
        View itemView = holder.itemView;
        //顺序id
        TextView orderTv = itemView.findViewById(R.id.tv_order_text);
        //标题
        TextView titleTv = itemView.findViewById(R.id.tv_title);
        //播放量
        TextView countsTv = itemView.findViewById(R.id.tv_item_play_counts);
        //播放总时长
        TextView durationTv = itemView.findViewById(R.id.tv_detail_item_duration);
        //更新日期
        TextView updateDataTv = itemView.findViewById(R.id.tv_detail_item_data);


        //设置数据
        Track track = mDetailData.get(position);
        orderTv.setText((position + 1) + "");
        titleTv.setText(track.getTrackTitle());
        countsTv.setText(String.valueOf(track.getPlayCount()));
        int durationMil = track.getDuration() * 1000;
        String duration = mDurationFormat.format(durationMil);
        durationTv.setText(duration);
        String updateTimeText = mUpdateDateFormat.format(track.getUpdatedAt());
        updateDataTv.setText(updateTimeText);
        //设置itemView点击器

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
//                Toast.makeText(itemView.getContext(), "you click " + position + "item", Toast.LENGTH_SHORT).show();
                if (mItemClickListener != null) {
                    //参数需要有列表和位置
                    mItemClickListener.onItemClick(mDetailData,position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        LogUtil.d(TAG, "" + mDetailData.size());
        return mDetailData.size();
    }

    public void setData(List<Track> tracks) {
        //清楚原来的数据
        mDetailData.clear();
        //更新UI
        mDetailData.addAll(tracks);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(List<Track> detailData, int position);
    }
}
