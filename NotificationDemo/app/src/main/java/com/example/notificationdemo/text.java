package com.example.notificationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wangyong
 * @date 1/12/21 11:44 PM
 */
public class text extends RecyclerView {
    private static final String TAG = "text";

    public text(Context context) {
        this(context, null);
    }

    public text(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public text(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_input_number, this, false);
        Log.d(TAG, "办公当");
        addView(inflate);
    }
}
