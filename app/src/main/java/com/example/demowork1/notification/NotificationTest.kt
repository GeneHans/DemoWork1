package com.example.demowork1.notification

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.demowork1.DemoApplication
import com.example.demowork1.R
import com.example.demowork1.anim.TestAnimActivity
import com.example.demowork1.annotation.AnnotationTestActivity
import com.example.demowork1.util.LogUtil
import java.util.*

class NotificationTest(var context: Context) {
    private var textTitle = "标题"
    private var textContent = "内容"
    private var notificationManagerCompat = NotificationManagerCompat.from(context)
    private var serviceAction = "com.demowork1.notification"

    /**
     * 设置Channel，Android 8.0以上需要设置
     */
    fun setNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * 创建常规通知
     */
    fun createRegularNotification(id: Int) {
        var notification = NotificationCompat.Builder(DemoApplication.mContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.arrow_right)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        //发送通知
        notificationManagerCompat.notify(id, notification)
    }

    /**
     * 创建大页面通知
     */
    fun createFullNotification(id: Int) {
        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.arrow_right)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "这里是大文本：Much longer text that cannot fit one line..." +
                                "这里是大文本：Much longer text that cannot fit one line..." +
                                "这里是大文本：Much longer text that cannot fit one line..."
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        //发送通知
        notificationManagerCompat.notify(id, notification)
    }

    /**
     * 创建可以点击动作的通知
     */
    fun createIntentNotification(id: Int) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, AnnotationTestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val intent1 = Intent(context, TestAnimActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent1: PendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.arrow_right)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            //此处设置addAction则会生成按钮点击，不设置则是用来设置全通知点击
            .addAction(
                R.drawable.icon_left_arrow, "输入框页",
                pendingIntent
            )
            .addAction(
                R.drawable.icon_left_arrow, "动画页",
                pendingIntent1
            )
            .build()
        notificationManagerCompat.notify(id, notification)
    }

    /**
     * 创建可以回复的消息
     */
    fun createEditTextNotification(id: Int) {
        // Key for the string that's delivered in the action's intent.
        var replyLabel: String = "test"
        var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }
        // Build a PendingIntent for the reply action to trigger.
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            action = serviceAction
            putExtra(EXTRA_NOTIFICATION_ID, 9)
            putExtra(INPUT_DATA, remoteInput.resultKey)
        }
        var replyPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                9, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        // Create the reply action and add the remote input.
        var action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.icon_left_arrow,
                "请输入内容", replyPendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.arrow_right)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(action)
            .build()
        notificationManagerCompat.notify(id, notification)
    }

    /**
     * 创建带有进度条的通知
     */
    fun createProgressNotification(id: Int) {
        setNotificationChannel()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(R.drawable.arrow_right)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(context).apply {
            // Issue the initial notification with zero progress
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(id, builder.build())
            var count = 1
            var timer = Timer()
            var timerTask = object : TimerTask() {
                override fun run() {
                    var data = 10 * count
                    LogUtil.d(data.toString())
                    count++
                    if (data > 100) {
                        timer.cancel()
                        builder.setContentText("Download complete")
                            .setProgress(0, 0, false)
                        notify(id, builder.build())
                    }
                    builder.setProgress(100, data, true)
                    notify(id, builder.build())
                }
            }
            timer.schedule(timerTask, 0,1000)

            // Do the job here that tracks the progress.
            // Usually, this should be in a
            // worker thread
            // To show progress, update PROGRESS_CURRENT and update the notification with:
            // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            // notificationManager.notify(notificationId, builder.build());

            // When done, update the notification one more time to remove the progress bar

        }
    }

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NotificationTest(
                DemoApplication.mContext
            )
        }
        const val INPUT_DATA = "input_data"
        const val KEY_TEXT_REPLY = "key_text_reply"
        const val CHANNEL_ID = ""

    }
}