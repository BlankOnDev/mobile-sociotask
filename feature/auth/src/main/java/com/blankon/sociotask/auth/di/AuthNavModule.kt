package com.blankon.sociotask.auth.di

import com.blankon.sociotask.auth.navigation.SignInNavGraphImpl
import com.blankon.sociotask.core.navigation.base.BaseNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavModule {
    @Binds
    @IntoSet
    abstract fun bindAuthNavGraph(navGraph: SignInNavGraphImpl): BaseNavGraph
}