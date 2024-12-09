package com.example.siassessment.data.store

import com.example.siassessment.data.repository.MatchDetailsRemote
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.response.base.Result
import javax.inject.Inject

class MatchDetailsRemoteDataStore @Inject constructor(
    private val matchDetailsRemote: MatchDetailsRemote
) :
    MatchDetailsRemote {
    override suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse> {
        return matchDetailsRemote.getIndNzMatchDetails()
    }

    override suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse> {
        return matchDetailsRemote.getSaPakMatchDetails()
    }
}