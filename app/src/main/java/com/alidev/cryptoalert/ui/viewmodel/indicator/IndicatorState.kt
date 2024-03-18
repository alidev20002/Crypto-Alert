package com.alidev.cryptoalert.ui.viewmodel.indicator

data class IndicatorDataState(
    val sma: Double = 0.0,
    val ema: Double = 0.0,
    val wma: Double = 0.0,
    val hma: Double = 0.0,
    val rsi: Double = 0.0,
    val macd: Double = 0.0,
)