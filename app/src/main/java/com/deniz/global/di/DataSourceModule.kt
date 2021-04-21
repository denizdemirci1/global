package com.deniz.global.di

import com.deniz.global.data.remote.datasources.UserDataSource
import com.deniz.global.data.remote.datasources.UserDataSourceImpl
import com.deniz.global.data.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Provides
    fun provideService(
        service: Service,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserDataSourceImpl(service, dispatcher)
    }
}
