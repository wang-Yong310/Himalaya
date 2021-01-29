package com.example.typedemo



import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notion_layout.*
import kotlin.math.min


class MainActivity : AppCompatActivity() {
    var TAG:String = "MainActivity"
    val CHANNEL_ID: String = "ChannelId"
    var notificationId: Int = 1
    var importance = NotificationManager.IMPORTANCE_DEFAULT
    var id = "chang_id"
    var name: CharSequence = "name"

    lateinit var myRemoteView: RemoteViews
    lateinit var notificationManager: NotificationManager
   lateinit var circle:ImageView
    private lateinit var notificationChannel: NotificationChannel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var pid: Int = Process.myPid()
        Log.d(TAG, "MyApplication onCreate")
        Log.d(TAG, "MyApplication pid is $pid")
 //       initView()
        initEvent()


    }

    private fun initView() {
        notification_large_icon.setImageBitmap(
                getRoundedCornerImage(
                        BitmapFactory.decodeResource(resources, R.drawable.image3)
                )
        )
    }

    private fun getRoundedCornerImage(bitmap: Bitmap): Bitmap? {
        val size = min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint().apply {
            isAntiAlias = true
        }
        val rect = Rect(0, 0, size, size)
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }




    private fun initEvent() {
        bt_notification.setOnClickListener{
            Log.d(TAG, "bt_notification")
            createNotificationChannel()
            sendMessage()
/*                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(
                            NotificationCompat.BigTextStyle()
                                    .bigText("Much longer text that cannot fit on line...")
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)*/
            /*with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }*/
        }
    }

    private fun sendMessage() {
        //android8.0以上手机使用改办法实现通知栏

        val intent = Intent(this, AlertDetails::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        myRemoteView = RemoteViews(packageName, R.layout.notion_layout)
        myRemoteView.setTextViewText(R.id.notification_title, "Title")
        myRemoteView.setTextViewText(R.id.notification_text, "这是自定义text")
        myRemoteView.setImageViewBitmap(R.id.notification_large_icon,BitmapFactory.decodeResource(resources, R.drawable.image3))
        myRemoteView.setImageViewBitmap(R.id.iv_small_Icon,getRoundedCornerImage(BitmapFactory.decodeResource(resources, R.drawable.image1)))
        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setAutoCancel(true)//点击通知栏的时候移除通知栏
                .setContentTitle("contentTitle")
                .setContentText("contenttext")
                .setContentIntent(pendingIntent)//跳转到指定的页面
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)//设置true表示是一个正在活动的后台任务，不会被清除掉，比如音乐播放器
                .setCustomBigContentView(myRemoteView)
                .setWhen(System.currentTimeMillis())
                .build();
        notificationManager.notify(1, notification);

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}