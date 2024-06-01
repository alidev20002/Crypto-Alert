package com.alidev.cryptoalert.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
    @SerialName("shortName")
    val shortName: String,

    @SerialName("lowPrice")
    val lowPrice: String = "0.0",

    @SerialName("highPrice")
    val highPrice: String = "0.0",

    @SerialName("openPrice")
    val openPrice: String = "0.0",

    @SerialName("latestPrice")
    val latestPrice: String = "0.0",

    @SerialName("change")
    val change: String = "0.0",

    @SerialName("icon")
    val icon: Int
)
