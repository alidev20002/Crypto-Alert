package com.alidev.cryptoalert.ui.viewmodel.stats

import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition

interface MarketState

data class CryptoMarketState(
    val cryptoConditions: List<CryptoCondition>,
    val cryptos: List<Crypto>,
    val dstCurrency: String
) : MarketState

object EmptyMarketState : MarketState
