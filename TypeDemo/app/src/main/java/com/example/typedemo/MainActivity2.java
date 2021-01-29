package com.example.typedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity {
    private static String TAG = "MainActivity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //rgba
//        getARGB("rgba(255, 255, 255, 0.5)");
        //
        initView();
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageTintList(ColorStateList.valueOf(Color.RED));
        imageView.setImageBitmap(getGradientBitmap(this,R.drawable.button3,new int[]{Color.GREEN}));
    }

    // 过滤颜色RGBA
    private static Float[] getARGB(String buttonColor) {
        String newStr = buttonColor.substring(5, buttonColor.length() - 1);
        String[] arr = newStr.split(",");
        Log.d(TAG, "arr" + Arrays.toString(arr));
        Float[] nums = new Float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nums[i] = Float.parseFloat(arr[i].trim());
            Log.d(TAG, i+" --> " + nums[i]);
        }
        return nums;
    }

    public static Bitmap addGradient(Bitmap originalBitmap, int[] colors) {//给originalBitmap着渐变色
        if (colors == null || colors.length == 0) {//默认色处理
            colors = new int[]{Color.parseColor("#ff9900"), Color.parseColor("#ff9900")};
        } else if (colors.length == 1) {//单色处理
            int[] newColor = new int[]{colors[0], colors[0]};
            colors = newColor;
        }
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Canvas canvas = new Canvas(originalBitmap);//Canvas中Bitmap是用来保存像素，相当于画纸
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, width, height, colors, null, Shader.TileMode.CLAMP);//shader:着色器，线性着色器设置渐变从左上坐标到右下坐标
        paint.setShader(shader);//设置着色器
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置图像混合模式
        canvas.drawRect(0, 0, width, height, paint);
        return originalBitmap;
    }

    public static Bitmap getGradientBitmap(Context context, int drawableId, int[] colors) {
        //android不允许直接修改res里面的图片，所以要用copy方法
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);
        addGradient(bitmap, colors);
        return bitmap;
    }

}