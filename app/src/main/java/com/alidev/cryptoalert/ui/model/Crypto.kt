package com.alidev.cryptoalert.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
    @SerialName("shortName")
    val shortName: String,

    @SerialName("lowPrice")
    val lowPrice: String = "",

    @SerialName("highPrice")
    val highPrice: String = "",

    @SerialName("openPrice")
    val openPrice: String = "",

    @SerialName("latestPrice")
    val latestPrice: String = "",

    @SerialName("change")
    val change: String = "",

    @SerialName("icon")
    val icon: Int
)
