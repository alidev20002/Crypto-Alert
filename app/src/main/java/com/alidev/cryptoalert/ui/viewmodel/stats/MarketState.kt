package com.alidev.cryptoalert.ui.viewmodel.stats

import com.alidev.cryptoalert.data.api.CryptoMarket
import com.alidev.cryptoalert.ui.model.CryptoCondition

interface MarketState

data class CryptoMarketState(
    val cryptoConditions: List<CryptoCondition>,
    val cryptoMarket: CryptoMarket,
    val dstCurrency: String
) : MarketState

object EmptyMarketState : MarketState
