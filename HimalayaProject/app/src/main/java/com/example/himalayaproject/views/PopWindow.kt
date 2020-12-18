package com.example.himalayaproject.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.himalayaproject.R
import com.example.himalayaproject.base.BaseApplication

/**
 * @author wangyong
 * @date 12/18/20 2:45 PM
 */
class PopWindow : PopupWindow() {
    //载入View
    init {
        val popView = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.pop_play_list, null, false)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        contentView = popView
        setWidth(width)
        setHeight(height)
        isOutsideTouchable = true
    }

}
