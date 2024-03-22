package com.alidev.cryptoalert.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.alidev.cryptoalert.MainActivity
import com.alidev.cryptoalert.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoAlertService : Service() {

    private var isServiceStarted = false

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "crypto_stats_channel",
                "Crypto Alert Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = NotificationCompat.Builder(this, "crypto_stats_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Crypto Alert")
            .setContentText("Price Checking...")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: return START_STICKY
        when(intent.action) {
            START -> {
                startServiceJob()
            }

            STOP -> {
                stopServiceJob()
                return START_NOT_STICKY
            }
        }

        return START_STICKY
    }

    private fun startServiceJob() {
        if (isServiceStarted) return

        isServiceStarted = true
        // service job...
    }

    private fun stopServiceJob() {
        try {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }catch (e: Exception) {
            Log.i("alitest", "stopServiceJob: ${e.message}")
            Toast.makeText(this, "Service stopped without being started", Toast.LENGTH_SHORT).show()
        }
        isServiceStarted = false
    }

    companion object {

        const val START = "START"
        const val STOP = "STOP"
    }
}