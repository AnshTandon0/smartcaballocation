package com.androidants.smartcaballocation.di

import com.androidants.smartcaballocation.data.ApiCalls
import com.androidants.smartcaballocation.data.repository.MainRepository
import com.androidants.smartcaballocation.data.repository.MainRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiCalls {
        return Retrofit.Builder()
            .baseUrl("https://stackoverflow.com/questions/64006085/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCalls::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiCalls): MainRepository {
        return MainRepositoryImpl(api , provideFirebaseAuth())
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
}