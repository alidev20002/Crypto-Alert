package com.alidev.cryptoalert.ui.viewmodel.indicator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepository
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndicatorViewModel @Inject constructor(
    private val indicatorRepository: IndicatorRepository,
    private val cryptoCandlesRepository: CryptoCandlesRepository
) : ViewModel() {

    private val _indicatorState = MutableStateFlow(IndicatorDataState())
    val indicatorState = _indicatorState.asStateFlow()

    fun getIndicators(
        symbol: String = "BTCUSDT",
        resolution: String = "60",
        numberOfCandles: Int = 500,
        period: Int = 9,
        shortPeriod: Int = 12,
        longPeriod: Int = 26,
        signalPeriod: Int = 9
    ) {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis() / 1000
            val startTime = currentTime - (numberOfCandles * 3600)
            val currentTime2 = startTime - 3600
            val startTime2 = currentTime2 - (500 * 3600)
            val candles1 = cryptoCandlesRepository.getCandles(symbol, resolution, startTime2, currentTime2)
            val candles2 = cryptoCandlesRepository.getCandles(symbol, resolution, startTime, currentTime)
            val candles = mutableListOf<Double>()
            candles1.close.forEach {
                candles.add(it)
            }
            candles2.close.forEach {
                candles.add(it)
            }
            val sma = indicatorRepository.sma(candles, period)
            val ema = indicatorRepository.ema(candles, period)
            val wma = indicatorRepository.wma(candles, period)
            val hma = indicatorRepository.hma(candles, period)
            val rsi = indicatorRepository.rsi(candles, period)
            val macd = indicatorRepository.macd(candles, shortPeriod, longPeriod, signalPeriod)
            _indicatorState.emit(
                IndicatorDataState(
                    sma = sma,
                    ema = ema,
                    wma = wma,
                    hma = hma,
                    rsi = rsi,
                    macd = macd
                )
            )
        }

    }

}