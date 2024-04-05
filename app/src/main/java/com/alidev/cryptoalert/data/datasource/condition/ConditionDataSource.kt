package com.alidev.cryptoalert.data.datasource.condition

import kotlinx.coroutines.flow.Flow

interface ConditionDataSource {

    fun readConditions(): Flow<String?>

    suspend fun writeConditions(value: String)

    fun readConditionsSync(): String?

    fun writeConditionsSync(value: String)
}