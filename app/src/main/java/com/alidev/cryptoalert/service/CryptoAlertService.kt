package com.alidev.cryptoalert.service

import android.app.Notification
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
import androidx.core.content.ContextCompat
import com.alidev.cryptoalert.MainActivity
import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.CryptoCondition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CryptoAlertService : Service() {

    @Inject
    lateinit var cryptoMarketRepository: CryptoMarketRepository

    @Inject
    lateinit var conditionRepository: ConditionRepository

    private var serviceJob: Job? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val notification = createNotification()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "crypto_stats_channel",
                "Crypto Alert Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

        return NotificationCompat.Builder(this, "crypto_stats_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Crypto Alert")
            .setContentText("Price Checking...")
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: return START_STICKY
        when(intent.action) {
            START -> {
                startServiceJob()
            }

            STOP -> {
                stopServiceJob()
            }
        }

        return START_STICKY
    }

    private fun startServiceJob() {
        if (isServiceStarted) return

        isServiceStarted = true

        val conditions = conditionRepository.readConditionsSync().toMutableList()
        val sourceCurrencies = conditions.distinctBy {
            it.crypto.name
        }.joinToString(separator = ",") { it.crypto.name }
        val destinationCurrency = "rls"

        serviceJob = GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                val market = cryptoMarketRepository.getStats(sourceCurrencies, destinationCurrency)
                val stats = market.cryptoStats

                val okConditions = mutableListOf<CryptoCondition>()

                conditions.forEach { cryptoCondition ->
                   val price = stats["${cryptoCondition.crypto.name}-$destinationCurrency"]?.latest
                   price?.let {
                       if (isConditionOk(price.toDouble(), cryptoCondition)) {
                           okConditions.add(cryptoCondition)
                           // create a notification with sound and vibration
                       }
                   }
                }

                conditions.removeAll(okConditions)

                conditionRepository.writeConditionsSync(conditions)

                if (conditions.isEmpty()) {
                    stopServiceJob()
                    return@launch
                }

                delay(DELAY_BETWEEN_REQUEST_MILLIS)
            }
        }
    }

    private fun isConditionOk(price: Double, crypto: CryptoCondition): Boolean {
        return when(crypto.condition) {
            Condition.INCREASE -> {
                price >= crypto.expectedPrice
            }

            Condition.DECREASE -> {
                price <= crypto.expectedPrice
            }
        }
    }

    private fun stopServiceJob() {
        try {
            serviceJob?.cancel()
            stopSelf()
        }catch (e: Exception) {
            Log.i("alitest", "stopServiceJob: ${e.message}")
            Toast.makeText(this, "Service stopped without being started", Toast.LENGTH_SHORT).show()
        }
        isServiceStarted = false
    }

    companion object {

        private const val NOTIFICATION_ID = 8112560
        private const val DELAY_BETWEEN_REQUEST_MILLIS = 15000L

        const val START = "START"
        const val STOP = "STOP"

        var isServiceStarted = false
            private set

        fun start(context: Context) {
            val intent = Intent(context, CryptoAlertService::class.java).also {
                it.action = START
            }

            ContextCompat.startForegroundService(context, intent)
        }

        fun stop(context: Context) {
            if (!isServiceStarted)
                return

            val intent = Intent(context, CryptoAlertService::class.java).apply {
                action = STOP
            }

            context.startService(intent)
        }
    }
}