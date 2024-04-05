package com.alidev.cryptoalert.data.api

import com.google.gson.annotations.SerializedName

data class CryptoStats(

    @SerializedName("latest")
    val latest: String,

    @SerializedName("dayLow")
    val dayLow: String,

    @SerializedName("dayHigh")
    val dayHigh: String,

    @SerializedName("dayOpen")
    val dayOpen: String,

    @SerializedName("dayChange")
    val dayChange: String
)
