package com.dev.james.launchlibraryapi.di

import android.app.Application
import androidx.room.Room
import com.dev.james.launchlibraryapi.data.local.LaunchDatabase
import com.dev.james.launchlibraryapi.data.remote.api.LaunchApi
import com.dev.james.launchlibraryapi.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LaunchAppModule {

    //setup okhttp interceptor + client
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    //provide our retrofit service using the client created
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //provide our api instance
    @Singleton
    @Provides
    fun provideLaunchApi(retrofit: Retrofit) : LaunchApi =
        retrofit.create(LaunchApi::class.java)

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    //provide database instance
    @Provides
    @Singleton
    fun provideDatabase(app : Application , callback : LaunchDatabase.Callback) =
        Room.databaseBuilder(app , LaunchDatabase :: class.java , "launch_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    fun provideDao(db : LaunchDatabase) =
        db.getDao()

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class  ApplicationScope