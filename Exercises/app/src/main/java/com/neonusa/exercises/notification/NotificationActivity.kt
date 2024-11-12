package com.neonusa.exercises.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.neonusa.exercises.Constants.NOTIFICATION_CHANNEL_NAME
import com.neonusa.exercises.Constants.NOTIFICATION_CHANNEl_ID
import com.neonusa.exercises.MainActivity
import com.neonusa.exercises.R
import com.neonusa.exercises.databinding.ActivityNotificationBinding

/*
    CATATAN NOTIFIKASI
    SYARAT UNTUK MENGAKTIFKAN NADA DERING + GETAR
        1. user harus mengizinkan notifikasi
        2. di pengaturan notifikasi, user harus mengizinkan nada dering + getar

    SATU CHANNEL BISA DIGUNAKAN UNTUK BANYAK NOTIFIKASI
    SEBUAH CHANNEL BISA PUNYA SETTING TERTENTU CONTOH :
    1. channel 1 : ringtone + getar
    2. channel 2 : getar saja tanpa ringtone
    bisa dibilang channel membantuk untuk mengkategorisasikan notifikasi
*/

// vibration pattern = delay,duration,delay,duration,delay.dst...

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationManager: NotificationManager

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // NOTIFICATION CHANNEL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEl_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(0, 1000)
            notificationManager.createNotificationChannel(channel)
        }
        //===============================================================================================

        // NOTIFIKASI 1 CHANNEL 1
        binding.btnNotification.setOnClickListener {
            sendNotification("Notifikasi 1 Channel 1","Ini notifikasi pertama di channel 1")
        }

        // NOTIFIKASI 2 CHANNEL 1
        binding.btnNotification2.setOnClickListener {
            sendBigTextNotification("Notifikasi 2 Channel 1", "Ini notifikasi kedua di channel 1")
        }

        // NOTIFIKASI 2 CHANNEL 1
        binding.btnNotification3.setOnClickListener {
            sendBigTextNotification1("Notifikasi 3 Channel 1", "Ini notifikasi ketiga di channel 1")
        }

        // Get the Vibrator service
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(this, alarmSound)
        binding.btnVibration.setOnClickListener {
            // Check if the device has a vibrator
            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // first argument is delay duration, second is vibration duration
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0,5000), -1))
                } else {
                    vibrator.vibrate(500)
                }
            }
        }

        binding.btnRingtone.setOnClickListener {
            ringtone.play()
        }
    }


    private fun sendNotification(title: String, message: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEl_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText("Pengingat")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        builder.setChannelId(NOTIFICATION_CHANNEl_ID)
        val notification = builder.build()
        notificationManager.notify(1, notification)
    }

    private fun sendBigTextNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEl_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        builder.setChannelId(NOTIFICATION_CHANNEl_ID)
        val notification = builder.build()
        notificationManager.notify(2, notification)  // Use a different ID for each notification
    }

    private fun sendBigTextNotification1(title: String, message: String) {
        // Intent untuk membuka aplikasi saat notifikasi diklik
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hanya buka jika belum terbuka
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEl_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        builder.setChannelId(NOTIFICATION_CHANNEl_ID)
        val notification = builder.build()
        notificationManager.notify(3, notification)  // Use a different ID for each notification
    }
}