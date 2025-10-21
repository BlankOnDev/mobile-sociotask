package com.blankon.sociotask.core.data.di

import android.content.Context
import com.blankon.sociotask.core.data.BuildConfig
import com.blankon.sociotask.core.data.BuildConfig.DEBUG
import com.blankon.sociotask.core.data.auth.repository.SessionRepositoryImpl
import com.blankon.sociotask.core.data.repository.AppRepository
import com.blankon.sociotask.core.data.repository.AppRepositoryImpl
import com.blankon.sociotask.core.data.source.local.AppDataStore
import com.blankon.sociotask.core.data.source.remote.ApiService
import com.blankon.sociotask.core.data.source.remote.AuthInterceptor
import com.blankon.sociotask.core.data.source.remote.TokenProvider
import com.blankon.sociotask.core.data.source.remote.TokenProviderImpl
import com.blankon.sociotask.core.domain.AppClock
import com.blankon.sociotask.core.domain.auth.repository.SessionRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRepository(apiService: ApiService): AppRepository = AppRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideTokenProvider(store: AppDataStore): TokenProvider =
        TokenProviderImpl(store)

    @Provides
    @Singleton
    fun provideSessionRepository(
        appDataStore: AppDataStore
    ): SessionRepository = SessionRepositoryImpl(appDataStore)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.Base_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(if (DEBUG) BODY else NONE))
        .addInterceptor(chuckerInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ) = ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).build()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = AppDataStore(context)

    @Provides
    @Singleton
    @AppClock
    fun provideAppClock(): Clock {
        return Clock.systemDefaultZone()
    }
}