package com.blankon.sociotask.core.data.di

import com.blankon.sociotask.core.data.auth.repository.AuthRepositoryImpl
import com.blankon.sociotask.core.data.auth.source.AuthDataRemoteSource
import com.blankon.sociotask.core.data.auth.source.FakeAuthDataSource
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

//
//    @Binds
//    @Singleton
//    fun provideRepository(apiService: ApiService): AppRepository =
//        AppRepositoryImpl(apiService)

    @Binds @Singleton
    abstract fun bindAuthRemoteDataSource(
        impl : FakeAuthDataSource
    ): AuthDataRemoteSource
}