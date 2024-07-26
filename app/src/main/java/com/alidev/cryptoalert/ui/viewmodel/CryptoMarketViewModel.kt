package com.alidev.cryptoalert.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alidev.cryptoalert.data.api.getCryptoIcon
import com.alidev.cryptoalert.data.api.toCryptoList
import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepository
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepository
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.dstcurrency.DstCurrencyRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
import com.alidev.cryptoalert.data.repository.theme.ThemeRepository
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoMarketViewModel @Inject constructor(
    private val conditionRepository: ConditionRepository,
    private val cryptoMarketRepository: CryptoMarketRepository,
    private val dstCurrencyRepository: DstCurrencyRepository,
    private val cryptoCandlesRepository: CryptoCandlesRepository,
    private val indicatorRepository: IndicatorRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private val cryptoConditionsAsFlow = conditionRepository.readConditions()

    private val cryptoStatsAsFlow = MutableStateFlow(getListOfAvailableCryptos())

    private var cryptoConditions = mutableListOf<CryptoCondition>()

    private val dstCurrencyAsFlow = dstCurrencyRepository.readDstCurrency()

    private val indicatorStateAsFlow = MutableStateFlow(IndicatorDataState())

    val isDarkMode = themeRepository.readTheme()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state: StateFlow<MarketState> = combine(
        cryptoConditionsAsFlow,
        cryptoStatsAsFlow,
        dstCurrencyAsFlow,
        indicatorStateAsFlow
    ) { conditions,
        stats,
        dstCurrency,
        indicator ->

        Log.i("alitest", "combine: update flow")

        cryptoConditions = conditions.toMutableList()

        CryptoMarketState(
            conditions,
            stats,
            dstCurrency,
            indicator.toMap()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyMarketState
    )

    init {
        val dstCurrency = dstCurrencyRepository.readDstCurrencySync()
        syncCryptoStats(dstCurrency)
        getIndicators()
    }

    fun syncCryptoStats(dstCurrency: String = "rls") {
        viewModelScope.launch {
            val cryptoStats = try {
                cryptoMarketRepository.getStats(SRC_CURRENCIES, dstCurrency).toCryptoList(dstCurrency)
            }catch (e: Exception) {
                getListOfAvailableCryptos()
            }
            cryptoStatsAsFlow.emit(cryptoStats)
        }
    }

    fun addCondition(condition: CryptoCondition) {
        viewModelScope.launch {
            cryptoConditions.add(condition)
            conditionRepository.writeConditions(cryptoConditions.toList())
        }
    }

    fun removeCondition(condition: CryptoCondition) {
        viewModelScope.launch {
            cryptoConditions.remove(condition)
            conditionRepository.writeConditions(cryptoConditions.toList())
        }
    }

    fun removeAllConditions() {
        viewModelScope.launch {
            cryptoConditions.clear()
            conditionRepository.writeConditions(cryptoConditions.toList())
        }
    }

    fun saveDstCurrency(dstCurrency: String) {
        viewModelScope.launch {
            dstCurrencyRepository.writeDstCurrency(dstCurrency)
        }
    }

    fun getIndicators(
        source: String = "close",
        symbol: String = "BTCUSDT",
        resolution: String = "60",
        numberOfCandles: Int = 500,
        period: Int = 9,
        shortPeriod: Int = 12,
        longPeriod: Int = 26,
        signalPeriod: Int = 9
    ) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis() / 1000
                val startTime = currentTime - (numberOfCandles * 3600)
                val candles = cryptoCandlesRepository.getCandles(symbol, resolution, startTime, currentTime)
                val sourceCandles = when (source) {
                    "close" -> candles.close
                    "open" -> candles.open
                    "high" -> candles.high
                    "low" -> candles.low
                    else -> candles.close
                }
                val sma = indicatorRepository.sma(sourceCandles, period)
                val ema = indicatorRepository.ema(sourceCandles, period)
                val wma = indicatorRepository.wma(sourceCandles, period)
                val hma = indicatorRepository.hma(sourceCandles, period)
                val rsi = indicatorRepository.rsi(sourceCandles, period)
                val macd = indicatorRepository.macd(sourceCandles, shortPeriod, longPeriod, signalPeriod)
                indicatorStateAsFlow.emit(
                    IndicatorDataState(
                        sma = String.format("%.2f", sma),
                        ema = String.format("%.2f", ema),
                        wma = String.format("%.2f", wma),
                        hma = String.format("%.2f", hma),
                        rsi = String.format("%.2f", rsi),
                        macd = String.format("%.2f", macd)
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            themeRepository.writeTheme(isDarkMode)
        }
    }

    private fun getListOfAvailableCryptos(): List<Crypto> {
        return SRC_CURRENCIES.split(",").map {
            Crypto(
                shortName = it.uppercase(),
                icon = getCryptoIcon(it)
            )
        }
    }

    companion object {
        private const val SRC_CURRENCIES = "btc,eth,ltc,usdt,xrp,bch,bnb,eos,xlm,etc,trx,doge,uni,dai," +
                "link,dot,aave,ada,shib,ftm,matic,axs,mana,sand,avax,mkr,gmt,usdc,sol,near,ape,qnt,apt," +
                "sushi,one,dao,ton,not,meme"

        fun getListOfAvailableCryptos(): List<Crypto> {
            return SRC_CURRENCIES.split(",").map {
                Crypto(
                    shortName = it.uppercase(),
                    lowPrice = "0.0",
                    highPrice = "0.0",
                    openPrice = "0.0",
                    latestPrice = "0.0",
                    change = "0.0",
                    icon = getCryptoIcon(it)
                )
            }
        }
    }
}