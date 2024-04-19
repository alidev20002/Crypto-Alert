package com.alidev.cryptoalert.utils

fun String.toFormattedPrice(): String {
    val split = this.split(".")
    val intPart = split[0]
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()

    val fractionPart = split.getOrNull(1)

    return if (fractionPart == null || fractionPart == "0") intPart else "$intPart.$fractionPart"
}