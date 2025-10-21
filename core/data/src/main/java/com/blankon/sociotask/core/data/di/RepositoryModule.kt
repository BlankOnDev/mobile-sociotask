package com.blankon.sociotask.core.data.di

import com.blankon.sociotask.core.data.auth.repository.AuthRepositoryImpl
import com.blankon.sociotask.core.data.auth.source.AuthDataRemoteSource
import com.blankon.sociotask.core.data.auth.source.AuthRemoteDataSourceImpl
import com.blankon.sociotask.core.data.dashboard.repository.FakeTaskRepository
import com.blankon.sociotask.core.data.utils.ConnectivityManagerNetworkMonitor
import com.blankon.sociotask.core.data.utils.NetworkMonitor
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import com.blankon.sociotask.core.domain.dashboard.repository.TaskRepository
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
//
//    @Binds
//    @Singleton
//    abstract fun bindAuthRemoteDataSource(
//        impl: FakeAuthDataSource
//    ): AuthDataRemoteSource


    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        remoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthDataRemoteSource


//    @Binds
//    @Singleton
//    abstract fun bindGoogleAuthDataSource(
//    ): GoogleAuthDataSource

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    internal abstract fun bindsTaskRepository(
        repo: FakeTaskRepository
    ): TaskRepository

}