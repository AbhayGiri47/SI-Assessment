package com.example.siassessment.di

import com.example.siassessment.data.remote.impl.MatchDetailsRemoteImpl
import com.example.siassessment.data.remote.services.MatchDetailsService
import com.example.siassessment.data.repository.MatchDetailsRemote
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module()
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    companion object {
        @Provides
        fun providesAuthService(retrofit: Retrofit): MatchDetailsService {
            return retrofit.create(MatchDetailsService::class.java)
        }
    }

    @Binds
    abstract fun bindsMatchDetailsRemote(matchDetailsRemoteImpl: MatchDetailsRemoteImpl): MatchDetailsRemote
}