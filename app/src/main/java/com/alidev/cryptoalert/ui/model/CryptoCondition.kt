package com.alidev.cryptoalert.ui.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCondition(
    @SerialName("crypto")
    val crypto: Crypto,

    @SerialName("expectedPrice")
    val expectedPrice: Double,

    @SerialName("condition")
    val condition: Condition,
)
