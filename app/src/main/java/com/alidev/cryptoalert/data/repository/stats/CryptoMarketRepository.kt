package com.alidev.cryptoalert.data.repository.stats

import com.alidev.cryptoalert.data.api.CryptoMarket

interface CryptoMarketRepository {

    suspend fun getStats(
        srcCurrency: String,
        dstCurrency: String
    ): CryptoMarket
}