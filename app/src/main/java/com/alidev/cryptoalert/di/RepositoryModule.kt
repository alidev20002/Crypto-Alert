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

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCryptoMarketRepository(
        repository: CryptoMarketRepositoryDefault
    ): CryptoMarketRepository

    @Binds
    fun bindCryptoConditionRepository(
        repository: ConditionRepositoryDefault
    ): ConditionRepository

    @Binds
    fun bindIndicatorRepository(
        repository: IndicatorRepositoryDefault
    ): IndicatorRepository

    @Binds
    fun bindCryptoCandlesRepository(
        repository: CryptoCandlesRepositoryDefault
    ): CryptoCandlesRepository
}