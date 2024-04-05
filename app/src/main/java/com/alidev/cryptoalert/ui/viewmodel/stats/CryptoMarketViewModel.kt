package com.alidev.cryptoalert.ui.viewmodel.stats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alidev.cryptoalert.data.api.getCryptoIcon
import com.alidev.cryptoalert.data.api.toCryptoList
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.dstcurrency.DstCurrencyRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
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
    private val dstCurrencyRepository: DstCurrencyRepository
) : ViewModel() {

    private val cryptoConditionsAsFlow = conditionRepository.readConditions()

    private val cryptoStatsAsFlow = MutableStateFlow(getListOfAvailableCryptos())

    private var cryptoConditions = mutableListOf<CryptoCondition>()

    private val dstCurrencyAsFlow = dstCurrencyRepository.readDstCurrency()

    val state: StateFlow<MarketState> = combine(
        cryptoConditionsAsFlow,
        cryptoStatsAsFlow,
        dstCurrencyAsFlow
    ) { conditions,
        stats,
        dstCurrency ->

        Log.i("alitest", "combine: update flow")

        cryptoConditions = conditions.toMutableList()

        CryptoMarketState(
            conditions,
            stats,
            dstCurrency
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyMarketState
    )

    init {
        val dstCurrency = dstCurrencyRepository.readDstCurrencySync()
        syncCryptoStats(dstCurrency)
    }

    fun syncCryptoStats(dstCurrency: String = "rls") {
        viewModelScope.launch {
            val cryptoStats = try {
                cryptoMarketRepository.getStats(SRC_CURRENCIES, dstCurrency).toCryptoList()
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

    fun saveDstCurrency(dstCurrency: String) {
        viewModelScope.launch {
            dstCurrencyRepository.writeDstCurrency(dstCurrency)
        }
    }

    private fun getListOfAvailableCryptos(): List<Crypto> {
        return SRC_CURRENCIES.split(",").map {
            Crypto(
                shortName = it,
                icon = getCryptoIcon(it)
            )
        }
    }

    companion object {
        private const val SRC_CURRENCIES = "btc,eth,ltc,usdt,xrp,bch,bnb,eos,xlm,etc," +
                "trx,doge,uni,dai,link,dot,aave,ada,shib"
    }
}