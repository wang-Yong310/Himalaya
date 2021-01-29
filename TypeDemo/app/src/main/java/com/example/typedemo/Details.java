package com.example.typedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * @author wangyong
 * @date 1/14/21 8:28 PM
 */
public class Details extends Activity {
    private static final String TAG = " Details";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
 //       initView();
        ImageView iv = findViewById(R.id.imageView3);
    }

    private void initView() {
        //未做圆角处理的原始图
        ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        image1.setImageResource(R.drawable.image1);
        //圆角图
       /* RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        roundedDrawable.getPaint().setAntiAlias(true);
        roundedDrawable.setCornerRadius(30);
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.setImageDrawable(roundedDrawable);*/

        //圆形图
        ImageView imageView =  findViewById(R.id.imageView3);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.image1));
        drawable.getPaint().setAntiAlias(true);
        drawable.setCornerRadius(600);
        imageView.setImageDrawable(drawable);


    }

}
