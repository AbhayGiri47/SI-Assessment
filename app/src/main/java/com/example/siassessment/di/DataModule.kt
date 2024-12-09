package com.example.siassessment.di

import com.example.siassessment.data.impl.MatchRepositoryImpl
import com.example.siassessment.domain.repository.MatchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsMatchDetailsRepository(matchRepositoryImpl: MatchRepositoryImpl):MatchRepository
}