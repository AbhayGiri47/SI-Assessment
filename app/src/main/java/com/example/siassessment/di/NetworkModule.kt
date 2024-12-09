package com.example.siassessment.di

import com.example.siassessment.data.remote.services.ConsumerServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofitService(): Retrofit {
        return ConsumerServiceFactory.makeRetrofitService()
    }

}