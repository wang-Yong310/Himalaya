package com.example.himalayaproject.ui.player;

import android.os.Bundle;

import com.example.himalayaproject.R;
import com.example.himalayaproject.base.BaseActivity;

/**
 * @author wangyong
 * @date 12/10/20 4:42 PM
 */
public class PlayerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //测试
        PlayerPresenter playerPresenter = PlayerPresenter.getPlayerPresenter();
        playerPresenter.play();
    }
}
