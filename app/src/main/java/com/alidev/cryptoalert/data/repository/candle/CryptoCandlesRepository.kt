package com.alidev.cryptoalert.data.repository.candle

import com.alidev.cryptoalert.data.api.CryptoCandles

interface CryptoCandlesRepository {

    suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CryptoCandles
}