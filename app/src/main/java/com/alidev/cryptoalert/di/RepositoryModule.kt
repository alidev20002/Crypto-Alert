package com.alidev.cryptoalert.di

import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepository
import com.alidev.cryptoalert.data.repository.candle.CryptoCandlesRepositoryDefault
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepository
import com.alidev.cryptoalert.data.repository.candle.IndicatorRepositoryDefault
import com.alidev.cryptoalert.data.repository.condition.ConditionRepository
import com.alidev.cryptoalert.data.repository.condition.ConditionRepositoryDefault
import com.alidev.cryptoalert.data.repository.dstcurrency.DstCurrencyRepository
import com.alidev.cryptoalert.data.repository.dstcurrency.DstCurrencyRepositoryDefault
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepository
import com.alidev.cryptoalert.data.repository.stats.CryptoMarketRepositoryDefault
import com.alidev.cryptoalert.data.repository.theme.ThemeRepository
import com.alidev.cryptoalert.data.repository.theme.ThemeRepositoryDefault
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
    fun bindDstCurrencyRepository(
        repository: DstCurrencyRepositoryDefault
    ): DstCurrencyRepository

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

    @Binds
    @Singleton
    fun bindCThemeRepository(
        repository: ThemeRepositoryDefault
    ): ThemeRepository
}