package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepository
import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepositoryDefault
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepository
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepositoryDefault
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.condition.ConditionRepositoryDefault
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepositoryDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCryptoMarketRepository(
        repository: CryptoMarketRepositoryDefault
    ): CryptoMarketRepository

    @Binds
    @Singleton
    fun bindCryptoConditionRepository(
        repository: ConditionRepositoryDefault
    ): ConditionRepository

    @Binds
    @Singleton
    fun bindIndicatorRepository(
        repository: IndicatorRepositoryDefault
    ): IndicatorRepository

    @Binds
    @Singleton
    fun bindCryptoCandlesRepository(
        repository: CryptoCandlesRepositoryDefault
    ): CryptoCandlesRepository
}