package com.alidev.cryptoalert.data.api

import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.ui.model.Crypto
import com.google.gson.annotations.SerializedName

data class CryptoMarket(

    @SerializedName("stats")
    val cryptoStats: Map<String, CryptoStats>
)

fun CryptoMarket.toCryptoList(dstCurrency: String): List<Crypto> {
    return cryptoStats.entries.toList().map {
        val shortName = it.key.split("-")[0]

        val latestPrice = if (dstCurrency == "rls") {
                it.value.latest.substring(0, it.value.latest.length - 1)
            } else{
                it.value.latest
            }

        Crypto(
            shortName = shortName.uppercase(),
            lowPrice = it.value.dayLow,
            highPrice = it.value.dayHigh,
            openPrice = it.value.dayOpen,
            latestPrice = latestPrice,
            change = it.value.dayChange,
            icon = getCryptoIcon(shortName)
        )
    }
}

fun getCryptoIcon(shortName: String): Int {
    return when(shortName) {
        "aave" -> R.drawable.aave
        "ada" -> R.drawable.ada
        "ape" -> R.drawable.ape
        "apt" -> R.drawable.apt
        "avax" -> R.drawable.avax
        "axs" -> R.drawable.axs
        "bch" -> R.drawable.bch
        "bnb" -> R.drawable.bnb
        "btc" -> R.drawable.btc
        "dai" -> R.drawable.dai
        "dao" -> R.drawable.dao
        "doge" -> R.drawable.doge
        "dot" -> R.drawable.dot
        "eos" -> R.drawable.eos
        "etc" -> R.drawable.etc
        "eth" -> R.drawable.eth
        "ftm" -> R.drawable.ftm
        "gmt" -> R.drawable.gmt
        "link" -> R.drawable.link
        "ltc" -> R.drawable.ltc
        "mana" -> R.drawable.mana
        "matic" -> R.drawable.matic
        "meme" -> R.drawable.meme
        "mkr" -> R.drawable.mkr
        "near" -> R.drawable.near
        "not" -> R.drawable.not
        "one" -> R.drawable.one
        "qnt" -> R.drawable.qnt
        "sand" -> R.drawable.sand
        "shib" -> R.drawable.shib
        "sol" -> R.drawable.sol
        "sushi" -> R.drawable.sushi
        "ton" -> R.drawable.ton
        "trx" -> R.drawable.trx
        "uni" -> R.drawable.uni
        "usdc" -> R.drawable.usdc
        "usdt" -> R.drawable.usdt
        "xlm" -> R.drawable.xlm
        "xrp" -> R.drawable.xrp
        else -> R.drawable.coin
    }
}
