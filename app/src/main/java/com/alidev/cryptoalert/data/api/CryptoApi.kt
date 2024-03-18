package com.alidev.cryptoalert.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("stats")
    suspend fun getStats(
        @Query("srcCurrency") src: String,
        @Query("dstCurrency") dst: String
    ) : CryptoMarket

    @GET("udf/history")
    suspend fun getHistory(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ) : CryptoCandles
}