package com.alidev.cryptoalert.data.api

import com.google.gson.annotations.SerializedName

data class CryptoStats(

    @SerializedName("latest")
    val latest: String
)
