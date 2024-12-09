package com.example.siassessment.data.store

import javax.inject.Inject

class MatchDetailsDataStoreFactory @Inject constructor(
    private val remoteDataStore: MatchDetailsRemoteDataStore
) {
    fun getRemoteDataStore(): MatchDetailsRemoteDataStore = remoteDataStore

}