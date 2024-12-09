package com.example.siassessment.domain.repository

import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.response.base.Result

interface MatchRepository {

    suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse>
    suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse>
}