package com.alidev.cryptoalert.data.datasource.stats

import com.alidev.cryptoalert.data.api.CryptoMarket

interface CryptoMarketDataSource {

    suspend fun getStats(
        srcCurrency: String,
        dstCurrency: String
    ): CryptoMarket
}