package com.example.siassessment.data.remote.impl

import com.example.siassessment.data.remote.services.MatchDetailsService
import com.example.siassessment.data.remote.utils.safeApiCall
import com.example.siassessment.data.repository.MatchDetailsRemote
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.response.base.Result
import javax.inject.Inject

class MatchDetailsRemoteImpl @Inject constructor(private val matchDetailsService: MatchDetailsService) :
    MatchDetailsRemote {
    override suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse> {
        return safeApiCall(
            apiCall = {
                matchDetailsService.getINDNZMatchDetails()
            }
        )
    }

    override suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse> {
        return safeApiCall(
            apiCall = {
                matchDetailsService.getSAPAKMatchDetails()
            }
        )
    }

}