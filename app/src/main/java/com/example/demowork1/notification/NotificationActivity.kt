package com.example.demowork1.notification

import android.app.NotificationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demowork1.R

class NotificationActivity : AppCompatActivity() {
    private lateinit var btnNotification1: Button
    private lateinit var notificationManager: NotificationManager
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        btnNotification1 = findViewById(R.id.btn_notification1)
        var notificationTest = NotificationTest.instance
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        btnNotification1.setOnClickListener {
            notificationTest.createProgressNotification(2)
        }
    }
}