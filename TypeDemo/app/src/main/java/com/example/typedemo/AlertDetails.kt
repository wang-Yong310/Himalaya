package com.example.typedemo

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*


class AlertDetails : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
    }

    private fun initView() {
        imageView1.setImageBitmap(
                getRoundedCornerImage(BitmapFactory.decodeResource(resources, R.drawable.image3)))
        imageView.setImageBitmap(
                getRoundedCornerImage2(BitmapFactory.decodeResource(resources, R.drawable.image3)))


    }

    private fun getRoundedCornerImage2(bitmap: Bitmap): Bitmap? {
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }

        val size = bitmap.width
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        Canvas(output).drawCircle(size / 2f, size / 2f, size / 2f, paint)

         return output
    }


    private fun getRoundedCornerImage(bitmap: Bitmap): Bitmap? {
        val size = Math.min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = Rect(0, 0, size, size)
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

}
