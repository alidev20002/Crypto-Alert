package com.alidev.cryptoalert.ui.viewmodel

data class IndicatorDataState(
    val sma: String = "0.0",
    val ema: String = "0.0",
    val wma: String = "0.0",
    val hma: String = "0.0",
    val rsi: String = "0.0",
    val macd: String = "0.0",
)

fun IndicatorDataState.toMap(): Map<String, String> {
    return mapOf(
        "SMA" to sma,
        "EMA" to ema,
        "WMA" to wma,
        "HMA" to hma,
        "RSI" to rsi,
        "MACD" to macd
    )
}