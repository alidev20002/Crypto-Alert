package com.alidev.cryptoalert.data.api

import com.alidev.cryptoalert.ui.model.Crypto
import com.google.gson.annotations.SerializedName

data class CryptoMarket(

    @SerializedName("stats")
    val cryptoStats: Map<String, CryptoStats>
)

fun CryptoMarket.toCryptoList(): List<Crypto> {
    return cryptoStats.entries.toList().map {
    val shortName = it.key.split("-")[0]
        Crypto(
            shortName = shortName,
            fullName = getFullName(shortName),
            lowPrice = it.value.dayLow,
            highPrice = it.value.dayHigh,
            openPrice = it.value.dayOpen,
            latestPrice = it.value.latest,
            change = it.value.dayChange,
            icon = getIcon(shortName)
        )
    }
}

private fun getFullName(shortName: String): String {
    return when(shortName) {
        "btc" -> "Bitcoin"
        "etc" -> "Etherium classic"
        else -> ""
    }
}

private fun getIcon(shortName: String): Int {
    return when(shortName) {
        "btc" -> 1
        "etc" -> 2
        else -> 3
    }
}
