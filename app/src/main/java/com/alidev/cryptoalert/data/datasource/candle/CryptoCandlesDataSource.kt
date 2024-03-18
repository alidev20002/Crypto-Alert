package com.alidev.cryptoalert.data.datasource.candle

import com.alidev.cryptoalert.data.api.CryptoCandles

interface CryptoCandlesDataSource {

    suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CryptoCandles
}