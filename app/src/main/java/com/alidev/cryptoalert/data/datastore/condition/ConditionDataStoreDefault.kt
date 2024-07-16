package com.alidev.cryptoalert.data.datastore.condition

import android.content.Context
import com.alidev.cryptoalert.data.datastore.KeyValueBase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConditionDataStoreDefault @Inject constructor(
    @ApplicationContext private val context: Context
) : ConditionDataStore {

    private val baseKeyValue = KeyValueBase(context)

    override fun readConditions(): Flow<String?> {
        return baseKeyValue.readString(CONDITIONS_KEY)
    }

    override suspend fun writeConditions(value: String) {
        baseKeyValue.writeString(CONDITIONS_KEY, value)
    }

    override fun readConditionsSync(): String? {
        return baseKeyValue.readStringSync(CONDITIONS_KEY)
    }

    override fun writeConditionsSync(value: String) {
        baseKeyValue.writeStringSync(CONDITIONS_KEY, value)
    }


    companion object {
        private const val CONDITIONS_KEY = "conditions"
    }
}