package com.alidev.cryptoalert.data.datastore

import kotlinx.coroutines.flow.Flow

interface ConditionDataStore {

    fun readConditions(): Flow<String?>

    suspend fun writeConditions(value: String)

    fun readConditionsSync(): String?

    fun writeConditionsSync(value: String)
}