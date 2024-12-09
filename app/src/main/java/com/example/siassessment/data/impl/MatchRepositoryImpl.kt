package com.example.siassessment.data.impl

import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.data.store.MatchDetailsDataStoreFactory
import com.example.siassessment.domain.repository.MatchRepository
import com.example.siassessment.domain.response.base.Result
import javax.inject.Inject


class MatchRepositoryImpl @Inject constructor(
    private val expressCheckDataStoreFactory: MatchDetailsDataStoreFactory
) : MatchRepository {

    override suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse>{
        return expressCheckDataStoreFactory.getRemoteDataStore().getIndNzMatchDetails()
    }

    override suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse> {
        return expressCheckDataStoreFactory.getRemoteDataStore().getSaPakMatchDetails()
    }

}