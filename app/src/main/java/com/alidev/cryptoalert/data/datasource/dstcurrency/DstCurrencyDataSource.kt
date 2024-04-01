package com.alidev.cryptoalert.data.datasource.dstcurrency

import kotlinx.coroutines.flow.Flow

interface DstCurrencyDataSource {

    fun readDstCurrency(): Flow<String?>

    suspend fun writeDstCurrency(value: String)

    fun readDstCurrencySync(): String?

    fun writeDstCurrencySync(value: String)
}