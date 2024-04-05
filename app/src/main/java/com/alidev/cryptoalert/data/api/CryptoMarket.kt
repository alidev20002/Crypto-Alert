package com.alidev.cryptoalert.data.api

import com.alidev.cryptoalert.R
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
            shortName = shortName.uppercase(),
            lowPrice = it.value.dayLow,
            highPrice = it.value.dayHigh,
            openPrice = it.value.dayOpen,
            latestPrice = it.value.latest,
            change = it.value.dayChange,
            icon = getCryptoIcon(shortName)
        )
    }
}

fun getCryptoIcon(shortName: String): Int {
    return when(shortName) {
        "aave" -> R.drawable.aave
        "ada" -> R.drawable.ada
        "bch" -> R.drawable.bch
        "bnb" -> R.drawable.bnb
        "btc" -> R.drawable.btc
        "dai" -> R.drawable.dai
        "doge" -> R.drawable.doge
        "dot" -> R.drawable.dot
        "eos" -> R.drawable.eos
        "etc" -> R.drawable.etc
        "eth" -> R.drawable.eth
        "link" -> R.drawable.link
        "ltc" -> R.drawable.ltc
        "shib" -> R.drawable.shib
        "trx" -> R.drawable.trx
        "uni" -> R.drawable.uni
        "usdt" -> R.drawable.usdt
        "xlm" -> R.drawable.xlm
        "xrp" -> R.drawable.xrp
        else -> R.drawable.coin
    }
}
