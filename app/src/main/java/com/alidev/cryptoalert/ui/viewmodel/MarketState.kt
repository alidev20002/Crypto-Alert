package com.alidev.cryptoalert.ui.viewmodel

import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition

interface MarketState

data class CryptoMarketState(
    val cryptoConditions: List<CryptoCondition>,
    val cryptos: List<Crypto>,
    val dstCurrency: String,
    val indicators: Map<String, String>
) : MarketState

object EmptyMarketState : MarketState
