package com.alidev.cryptoalert.data.repository.candle

import com.alidev.cryptoalert.data.datasource.candle.IndicatorDataSource
import javax.inject.Inject

class IndicatorRepositoryDefault @Inject constructor(
    private val indicatorDataSource: IndicatorDataSource,
) : IndicatorRepository {

    override suspend fun sma(prices: List<Double>, period: Int): Double {
        return indicatorDataSource.sma(prices, period)
    }

    override suspend fun ema(prices: List<Double>, period: Int): Double {
        return indicatorDataSource.ema(prices, period)
    }

    override suspend fun wma(prices: List<Double>, period: Int): Double {
        return indicatorDataSource.wma(prices, period)
    }

    override suspend fun hma(prices: List<Double>, period: Int): Double {
        return indicatorDataSource.hma(prices, period)
    }

    override suspend fun rsi(prices: List<Double>, period: Int): Double {
        return indicatorDataSource.rsi(prices, period)
    }

    override suspend fun macd(
        prices: List<Double>,
        shortPeriod: Int,
        longPeriod: Int,
        signalPeriod: Int
    ): Double {
        return indicatorDataSource.macd(prices, shortPeriod, longPeriod, signalPeriod)
    }
}