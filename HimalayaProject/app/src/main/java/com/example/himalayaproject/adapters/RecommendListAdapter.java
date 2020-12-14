package com.example.himalayaproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalayaproject.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private final static String TAG = "RecommendListAdapter";
    private List<Album> mData = new ArrayList<>();
    private OnRecommendItemClickListener mItemClickListener;

    /**
     * 这里找到View下一步封装数据
     *
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //为什么要传parent,因为在测量的时候要参考老爸，否则会出现问题，false 不绑定的意思
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        return new InnerHolder(itemView);
    }

    /**
     * 这里设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null) {
                    int clickPosition = (int) v.getTag();
                    mItemClickListener.onItemClick(clickPosition, mData.get(clickPosition));
                }
            }
        });
        holder.setData(mData.get(position));

    }

    /**
     * 返回要显示的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setDate(List<Album> albumList) {
        if (mData != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        //更新ui
        notifyDataSetChanged();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            //找到各个控件，设置数据
            //专辑的头像
            ImageView albumCoverIv = itemView.findViewById(R.id.iv_album_cover);
            //Title
            TextView albumTitleTv = itemView.findViewById(R.id.tv_album_title);
            //描述
            TextView albumDescriptionTv = itemView.findViewById(R.id.tv_album_description);
            //播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.tv_album_play_count);
            //内容数量
            TextView albumCountSizeTv = itemView.findViewById(R.id.tv_album_count_size);

            //设置Title
            albumTitleTv.setText(album.getAlbumTitle());
            //设置描述
            albumDescriptionTv.setText(album.getAlbumIntro());
            //设置播放次数
            albumPlayCountTv.setText("" + album.getPlayCount());
            //设置播放内容数量
            albumCountSizeTv.setText(album.getIncludeTrackCount() + "");
            //设置专辑封面
            Picasso.with(itemView.getContext()).load(album.getCoverUrlLarge()).into(albumCoverIv);

        }
    }

    public void setOnRecommendItemClickListener(OnRecommendItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public interface  OnRecommendItemClickListener {
        void onItemClick(int position, Album album);
    }
}
