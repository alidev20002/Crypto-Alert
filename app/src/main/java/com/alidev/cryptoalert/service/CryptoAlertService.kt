package com.alidev.cryptoalert.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.alidev.cryptoalert.MainActivity
import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.data.api.getCryptoIcon
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.dstcurrency.DstCurrencyRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.utils.toFormattedPrice
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

    @Inject
    lateinit var dstCurrencyRepository: DstCurrencyRepository

    private lateinit var notificationManager: NotificationManager

    private lateinit var ringtone: Ringtone

    private var serviceJob: Job? = null

    private var cryptoNotificationId = 10001

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        val notification = createNotification(
            title = "Crypto Alert",
            message = "Price Checking...",
            smallIcon = R.drawable.coin2,
            isOngoing = true
        )

        ringtone = prepareRingtone()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "crypto_stats_channel",
                "Crypto Alert Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(
        title: String,
        message: String,
        smallIcon: Int,
        isOngoing: Boolean
    ): Notification {

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        return NotificationCompat.Builder(this, "crypto_stats_channel")
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setOngoing(isOngoing)
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
            it.crypto.shortName
        }.joinToString(separator = ",") { it.crypto.shortName }
        val destinationCurrency = dstCurrencyRepository.readDstCurrencySync()

        serviceJob = GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {

                delay(DELAY_BETWEEN_REQUEST_MILLIS)

                if (ringtone.isPlaying)
                    ringtone.stop()

                try {
                    val market = cryptoMarketRepository.getStats(sourceCurrencies, destinationCurrency)
                    val stats = market.cryptoStats

                    val okConditions = mutableListOf<CryptoCondition>()

                    conditions.forEach { cryptoCondition ->
                        val cryptoStat =
                            stats["${cryptoCondition.crypto.shortName}-$destinationCurrency"]?.latest

                        val price = if (destinationCurrency == "rls") {
                            String.format("%.0f", cryptoStat?.toFloat()?.div(10))
                        } else{
                            cryptoStat
                        }

                        price?.let {
                            if (isConditionOk(price.toDouble(), cryptoCondition)) {
                                okConditions.add(cryptoCondition)
                                // create a notification with sound and vibration
                                val notification = createNotification(
                                    title = "Crypto Alert",
                                    message = "${cryptoCondition.crypto.shortName} is reached to ${price.toFormattedPrice()}",
                                    smallIcon = getCryptoIcon(cryptoCondition.crypto.shortName),
                                    isOngoing = false
                                )

                                notificationManager.notify(cryptoNotificationId, notification)
                                cryptoNotificationId += 1

                                if (!ringtone.isPlaying)
                                    ringtone.play()
                            }
                        }
                    }

                    conditions.removeAll(okConditions)

                    conditionRepository.writeConditionsSync(conditions)

                    if (conditions.isEmpty()) {
                        delay(DELAY_BETWEEN_REQUEST_MILLIS)
                        stopServiceJob()
                        return@launch
                    }

                }catch (e: Exception) {
                    Log.i("alitest", "startServiceJob: ${e.message}")
                }
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
            if (ringtone.isPlaying)
                ringtone.stop()
            stopSelf()
        }catch (e: Exception) {
            Log.i("alitest", "stopServiceJob: ${e.message}")
            Toast.makeText(this, "Service stopped without being started", Toast.LENGTH_SHORT).show()
        }
        isServiceStarted = false
    }

    private fun prepareRingtone(): Ringtone {
        val ringtoneUri = getAlarmRingtoneUri()
        return RingtoneManager.getRingtone(this, ringtoneUri)
    }

    private fun getAlarmRingtoneUri(): Uri {
        return RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_ALARM
        ) ?: Uri.parse("android.resource://$packageName/${R.raw.kaptainpolka}")
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