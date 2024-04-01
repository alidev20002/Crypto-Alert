package com.alidev.cryptoalert.ui.viewmodel.stats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alidev.cryptoalert.data.api.CryptoMarket
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
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
    private val cryptoMarketRepository: CryptoMarketRepository
) : ViewModel() {

    private val cryptoConditionsAsFlow = conditionRepository.readConditions()

    private val cryptoStateAsFlow = MutableStateFlow(CryptoMarket((emptyMap())))

    private var cryptoConditions = mutableListOf<CryptoCondition>()

    val state: StateFlow<MarketState> = combine(
        cryptoConditionsAsFlow,
        cryptoStateAsFlow
    ) { conditions,
        stats ->

        Log.i("alitest", "combine: update flow")

        cryptoConditions = conditions.toMutableList()

        CryptoMarketState(
            conditions,
            stats
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        EmptyMarketState
    )

    init {

        syncCryptoStats()
    }

    fun syncCryptoStats() {
        viewModelScope.launch {
            val cryptoMarket = try {
                cryptoMarketRepository.getStats(SRC_CURRENCIES, "rls")
            }catch (e: Exception) {
                CryptoMarket(emptyMap())
            }
            cryptoStateAsFlow.emit(cryptoMarket)
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

    companion object {
        private const val SRC_CURRENCIES = "btc,eth,ltc,usdt,xrp,bch,bnb,eos,xlm,etc," +
                "trx,doge,uni,dai,link,dot,aave,ada,shib"
    }
}