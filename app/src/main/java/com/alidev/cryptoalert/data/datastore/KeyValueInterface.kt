package com.alidev.cryptoalert.data.datastore

import kotlinx.coroutines.flow.Flow

interface KeyValueInterface {

    fun readString(key: String): Flow<String?>

    suspend fun writeString(key: String, value: String)

    fun readStringSync(key: String): String?

    fun writeStringSync(key: String, value: String)

}