package com.deniz.global.di

import com.deniz.global.data.remote.datasources.UserDataSource
import com.deniz.global.data.repositories.UserRepository
import com.deniz.global.data.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(
        dataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(dataSource)
    }
}
