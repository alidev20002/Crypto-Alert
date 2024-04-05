package com.alidev.cryptoalert.data.repository.dstcurrency

import kotlinx.coroutines.flow.Flow

interface DstCurrencyRepository {

    fun readDstCurrency(): Flow<String>

    suspend fun writeDstCurrency(value: String)

    fun readDstCurrencySync(): String

    fun writeDstCurrencySync(value: String)
}