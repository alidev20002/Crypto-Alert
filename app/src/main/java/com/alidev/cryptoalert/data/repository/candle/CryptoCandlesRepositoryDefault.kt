package com.alidev.cryptoalert.data.repository.candle

import com.alidev.cryptoalert.data.api.CryptoCandles
import com.alidev.cryptoalert.data.datasource.candle.CryptoCandlesDataSource
import javax.inject.Inject

class CryptoCandlesRepositoryDefault @Inject constructor(
    private val cryptoCandlesDataSource: CryptoCandlesDataSource
) : CryptoCandlesRepository {

    override suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CryptoCandles {
        return cryptoCandlesDataSource.getCandles(symbol, resolution, from, to)
    }
}