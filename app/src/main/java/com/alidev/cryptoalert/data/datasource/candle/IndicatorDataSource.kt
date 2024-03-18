package com.alidev.cryptoalert.data.datasource.candle


interface IndicatorDataSource {

    suspend fun sma(prices: List<Double>, period: Int): Double

    suspend fun ema(prices: List<Double>, period: Int): Double

    suspend fun wma(prices: List<Double>, period: Int): Double

    suspend fun hma(prices: List<Double>, period: Int): Double

    suspend fun rsi(prices: List<Double>, period: Int): Double

    suspend fun macd(prices: List<Double>, shortPeriod: Int, longPeriod: Int, signalPeriod: Int): Double
}