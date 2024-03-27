package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.datastore.ConditionDataStoreDefault
import com.alidev.cryptoalert.data.datastore.ConditionDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindConditionDataStore(
        dataStore: ConditionDataStoreDefault
    ): ConditionDataStore
}