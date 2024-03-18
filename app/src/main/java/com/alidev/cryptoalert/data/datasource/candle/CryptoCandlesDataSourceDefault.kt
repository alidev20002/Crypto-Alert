package com.alidev.cryptoalert.data.datasource.candle

import com.alidev.cryptoalert.data.api.CryptoApi
import com.alidev.cryptoalert.data.api.CryptoCandles
import javax.inject.Inject

class CryptoCandlesDataSourceDefault @Inject constructor(
    private val cryptoApi: CryptoApi
) : CryptoCandlesDataSource {

    override suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CryptoCandles {
        return cryptoApi.getHistory(symbol, resolution, from, to)
    }

}