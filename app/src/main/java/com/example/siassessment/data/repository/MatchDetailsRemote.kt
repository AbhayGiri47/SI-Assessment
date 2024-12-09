package com.example.siassessment.data.repository

import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.response.base.Result

interface MatchDetailsRemote {
    suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse>
    suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse>
}