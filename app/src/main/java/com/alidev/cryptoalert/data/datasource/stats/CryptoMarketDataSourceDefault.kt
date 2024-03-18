package com.alidev.cryptoalert.data.datasource.stats

import com.alidev.cryptoalert.data.api.CryptoApi
import javax.inject.Inject

class CryptoMarketDataSourceDefault @Inject constructor(
    private val cryptoApi: CryptoApi
) : CryptoMarketDataSource {

    override suspend fun getStats(srcCurrency: String, dstCurrency: String) =
        cryptoApi.getStats(srcCurrency, dstCurrency)
}