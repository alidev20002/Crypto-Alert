package com.alidev.cryptoalert.data.repository.stats

import com.alidev.cryptoalert.data.datasource.stats.CryptoMarketDataSource
import javax.inject.Inject

class CryptoMarketRepositoryDefault @Inject constructor(
    private val cryptoMarketDataSource: CryptoMarketDataSource
) : CryptoMarketRepository {

    override suspend fun getStats(srcCurrency: String, dstCurrency: String) =
        cryptoMarketDataSource.getStats(srcCurrency, dstCurrency)
}