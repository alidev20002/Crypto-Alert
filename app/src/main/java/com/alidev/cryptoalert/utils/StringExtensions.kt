package com.alidev.cryptoalert.utils

import java.text.DecimalFormat

fun String.toFormattedPrice(dstCurrency: String): String {
    val decimalFormat = DecimalFormat("###,###,###,###")
    var number = this.toFloat()
    if (dstCurrency == "rls")
        number /= 10
    return decimalFormat.format(number)
}