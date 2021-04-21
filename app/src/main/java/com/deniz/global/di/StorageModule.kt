package com.deniz.global.di

import com.deniz.global.util.storage.SharedPreferencesStorage
import com.deniz.global.util.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}
