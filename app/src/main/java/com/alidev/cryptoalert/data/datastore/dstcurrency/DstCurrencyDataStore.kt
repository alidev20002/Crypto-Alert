package com.alidev.cryptoalert.data.datastore.dstcurrency

import kotlinx.coroutines.flow.Flow

interface DstCurrencyDataStore {

    fun readDstCurrency(): Flow<String?>

    suspend fun writeDstCurrency(value: String)

    fun readDstCurrencySync(): String?

    fun writeDstCurrencySync(value: String)
}