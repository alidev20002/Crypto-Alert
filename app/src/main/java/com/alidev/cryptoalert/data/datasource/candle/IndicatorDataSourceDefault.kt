package com.alidev.cryptoalert.data.datasource.candle

import com.alidev.cryptoalert.data.utils.IndicatorCalculator
import javax.inject.Inject

class IndicatorDataSourceDefault @Inject constructor(): IndicatorDataSource {

    override suspend fun sma(prices: List<Double>, period: Int): Double {
        return IndicatorCalculator.sma(prices, period)
    }

    override suspend fun ema(prices: List<Double>, period: Int): Double {
        return IndicatorCalculator.ema(prices, period)
    }

    override suspend fun wma(prices: List<Double>, period: Int): Double {
        return IndicatorCalculator.wma(prices, period)
    }

    override suspend fun hma(prices: List<Double>, period: Int): Double {
        return IndicatorCalculator.hma(prices, period)
    }

    override suspend fun rsi(prices: List<Double>, period: Int): Double {
        return IndicatorCalculator.rsi(prices, period)
    }

    override suspend fun macd(
        prices: List<Double>,
        shortPeriod: Int,
        longPeriod: Int,
        signalPeriod: Int
    ): Double {
        return IndicatorCalculator.macd(prices, shortPeriod, longPeriod, signalPeriod)
    }
}