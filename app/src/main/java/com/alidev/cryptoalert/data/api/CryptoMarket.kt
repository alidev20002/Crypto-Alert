package com.alidev.cryptoalert.data.api

import com.google.gson.annotations.SerializedName

data class CryptoMarket(

    @SerializedName("stats")
    val cryptoStats: Map<String, CryptoStats>
)