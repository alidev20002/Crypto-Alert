package com.alidev.cryptoalert.data.api

import com.google.gson.annotations.SerializedName

data class CryptoCandles(
    @SerializedName("o")
    val open: List<Double>,

    @SerializedName("h")
    val high: List<Double>,

    @SerializedName("c")
    val close: List<Double>,

    @SerializedName("l")
    val low: List<Double>,
)