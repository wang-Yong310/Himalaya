package com.example.himalayaproject.ui.detail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalayaproject.base.BaseApplication;
import com.example.himalayaproject.ui.player.IPlayerViewCallback;
import com.example.himalayaproject.ui.player.PlayerActivity;
import com.example.himalayaproject.R;
import com.example.himalayaproject.adapters.DetailListAdapter;
import com.example.himalayaproject.base.BaseActivity;
import com.example.himalayaproject.ui.player.PlayerPresenter;
import com.example.himalayaproject.utils.ImageBlur;
import com.example.himalayaproject.utils.LogUtil;
import com.example.himalayaproject.views.UILoader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author wangyong
 * @date 12/6/20 4:08 PM
 */
public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, DetailListAdapter.ItemClickListener, IPlayerViewCallback {

    private ImageView mLargeCover;
    private ImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter mAlbumDetailPresent;
    private int mCurrentPage = 1;

    private final static String TAG = "DetailActivity";
    private RecyclerView mDetailList;
    private DetailListAdapter mDetailListAdapter;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private int mCurrentId = 1;
    private TextView mPlayControlTV;
    private ImageView mPlayControlBtn;
    private PlayerPresenter mPlayerPresenter;
    private List<Track> mCurrentTracks = null;
    private final static int DEFAULT_PLAY_INDEX = 0;
    private TwinklingRefreshLayout mRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        //设置状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
        initListener();
        //这个是播放详情的present
        mAlbumDetailPresent = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresent.registerViewCallback(this);
        //这个是播放器的present
        mPlayerPresenter = PlayerPresenter.getPlayerPresenter();
        mPlayerPresenter.registerViewCallback(this);
        updatePlayState(mPlayerPresenter.isPlaying());
    }


    /**
     * 初始化监听事件
     */
    private void initListener() {
        mPlayControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断播放器是否有播放列表
                //TODO：
                boolean has = mPlayerPresenter.havePlayList();
                if (has) {
                    handlePlayControl();
                } else {
                    handleNoPlayControl();
                }
            }
        });
    }

    /**
     * 当播放器里面没有播放内容，就要进行处理
     */
    private void handleNoPlayControl() {
        mPlayerPresenter.setPlayList(mCurrentTracks, DEFAULT_PLAY_INDEX);
    }

    private void handlePlayControl() {
        // 控制播放器的状态
        if (mPlayerPresenter.isPlaying()) {
            //正在播放，j就暂停
            mPlayerPresenter.pause();
        } else {
            mPlayerPresenter.play();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mDetailListContainer = findViewById(R.id.detail_list_container);
        //UILoader的创建
        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);
            mUiLoader.setOnRetryClickListener(DetailActivity.this);
        }
        mLargeCover = findViewById(R.id.iv_target_cover);
        mSmallCover = findViewById(R.id.iv_small_cover);
        mAlbumTitle = findViewById(R.id.tv_album_title);
        mAlbumAuthor = findViewById(R.id.tv_album_author);
        //播放控制的图标
        mPlayControlBtn = findViewById(R.id.detail_play_control);
        mPlayControlTV = findViewById(R.id.detail_play_tv);
    }

    private View createSuccessView(ViewGroup container) {
        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list, container, false);
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        mRefreshLayout =detailListView.findViewById(R.id.refresh_layout);
        //Recyclerview 的使用步骤
        // 第一步 : 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDetailList.setLayoutManager(layoutManager);
        // 第二步 ： 设置适配器
        mDetailListAdapter = new DetailListAdapter();
        mDetailList.setAdapter(mDetailListAdapter);
        //设置mDetailList的上下间距
        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 0);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 8);
                outRect.left = UIUtil.dip2px(view.getContext(), 2);
                outRect.right = UIUtil.dip2px(view.getContext(), 2);
            }
        });
        mDetailListAdapter.setItemClickListener(this);
        if (mRefreshLayout != null) {

            mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    super.onLoadMore(refreshLayout);
                    BaseApplication.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailActivity.this, "开始下拉刷新...", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                }

                @Override
                public void onFinishRefresh() {
                    super.onFinishRefresh();
                    BaseApplication.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailActivity.this, "开始上啦加载更多...", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                }
            });
        }
        return detailListView;
    }

    /**
     * @param tracks
     */
    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        this.mCurrentTracks = tracks;
        //判断数据结果，根据结果控制UI显示
        if (tracks == null || tracks.size() == 0) {
            if (mUiLoader != null) {
                mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
            }
        }
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
        //更新设置UI
        mDetailListAdapter.setData(tracks);
    }

    @Override
    public void onAlbumLoaded(Album album) {
        long id = 0;
        if (album != null) {
            id = album.getId();
        }
        //获取专辑的详情内容
        LogUtil.d(TAG, "album ID = " + id);
        mCurrentId = (int) id;
        if (mAlbumDetailPresent != null) {
            mAlbumDetailPresent.getAlbumDetail((int) album.getId(), mCurrentPage);
        }
        //拿数据，显示loading状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (mAlbumTitle != null) {
            mAlbumTitle.setText(album.getAlbumTitle());
        }
        if (mAlbumAuthor != null) {
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        }
        //做模糊效果 *（毛玻璃效果）
        if (mLargeCover != null && null != mLargeCover) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    //                  LogUtil.d(TAG, "thread -->" + Thread.currentThread().getName());
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null) {
                        //到这里说明有图片
                        ImageBlur.makeBlur(mLargeCover, DetailActivity.this);
                    }
                }

                @Override
                public void onError() {
                    LogUtil.d(TAG, "mLargeCover error");
                }
            });
        }

        if (mSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mSmallCover);
        }

    }

    @Override
    public void onNetWorkError(int errorCode, String errorMsg) {
        //请求发生错误显示网络异常状态
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    /**
     * 用户网络不佳的时候点击了刷新屏幕
     * 网络不佳时候点击屏幕刷新
     *
     * @return
     */
    @Override
    public void onRetryClick() {
        if (mAlbumDetailPresent != null) {
            LogUtil.d(TAG, "点击");
            mAlbumDetailPresent.getAlbumDetail(mCurrentId, mCurrentPage);
        }
    }

    @Override
    public void onItemClick(List<Track> detailData, int position) {
        LogUtil.d(TAG, "跳转");
        //设置播放器的数据
        PlayerPresenter playerPresenter = PlayerPresenter.getPlayerPresenter();
        playerPresenter.setPlayList(detailData, position);
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    /**
     * 根据播放状态修改图标和文字
     *
     * @param playing
     */
    private void updatePlayState(boolean playing) {
        //修改图标为暂停的状态，文字修改为正在播放
        if (mPlayControlTV != null && mPlayControlBtn != null) {
            mPlayControlBtn.setImageResource(playing ? R.drawable.selector_play_conrorl_pause : R.drawable.selector_play_conrorl_play);
            mPlayControlTV.setText(playing ? R.string.playing_text : R.string.pause_text);
        }
    }


    @Override
    public void onPlayStart() {
        updatePlayState(true);
    }

    @Override
    public void onPlayPause() {
        updatePlayState(false);
    }

    @Override
    public void onPlayStop() {
        updatePlayState(false);
    }


    @Override
    public void onPlayError() {

    }

    @Override
    public void nextPlay(@Nullable Track track) {

    }

    @Override
    public void onPrePlay(@Nullable Track track) {

    }

    @Override
    public void onListLoaded(@NotNull List<? extends Track> list) {

    }

    @Override
    public void onPlayModeChange(@NotNull XmPlayListControl.PlayMode playMode) {

    }

    @Override
    public void onProgressChange(int currentProgress, int total) {

    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinish() {

    }

    @Override
    public void onTrackUpdate(@Nullable Track track, int playIndex) {

    }

    @Override
    public void updateListOrder(boolean isReverse) {

    }
}
