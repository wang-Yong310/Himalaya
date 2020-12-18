package com.example.himalayaproject.base
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.himalayaproject.utils.LogUtil

/**
 * @author wangyong
 * @date 12/17/20 9:59 AM
 */
interface BasePageTransformer : ViewPager.PageTransformer{

    override fun transformPage(view: View, position: Float) {
        if (position <= 0.0f) {
            touch2Left(view, position);
        } else if (position <= 1.0f) {
            //从左向右滑动为当前View
            touch2Right(view, position);
        }
    }
    fun touch2Left(view: View,position: Float)

    fun touch2Right(view: View,position: Float)

}