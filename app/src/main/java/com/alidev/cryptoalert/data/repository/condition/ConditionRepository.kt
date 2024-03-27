package com.alidev.cryptoalert.data.repository.condition

import com.alidev.cryptoalert.ui.model.CryptoCondition
import kotlinx.coroutines.flow.Flow

interface ConditionRepository {

    fun readConditions(): Flow<List<CryptoCondition>>

    suspend fun writeConditions(conditions: List<CryptoCondition>)

    fun readConditionsSync(): List<CryptoCondition>

    fun writeConditionsSync(conditions: List<CryptoCondition>)
}