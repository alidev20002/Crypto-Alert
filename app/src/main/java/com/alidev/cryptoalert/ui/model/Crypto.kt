package com.alidev.cryptoalert.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
    @SerialName("name")
    val name: String,

    @SerialName("lowPrice")
    val lowPrice: Double = 0.0,

    @SerialName("highPrice")
    val highPrice: Double = 0.0,

    @SerialName("openPrice")
    val openPrice: Double = 0.0,

    @SerialName("latestPrice")
    val latestPrice: Double = 0.0,

    @SerialName("change")
    val change: Double = 0.0,

    @SerialName("icon")
    val icon: Int
)
