package com.example.siassessment.domain.usecase

import android.util.Log
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.repository.MatchRepository
import com.example.siassessment.domain.response.base.Result
import javax.inject.Inject

class MatchDetailsUseCase @Inject constructor(private val matchRepository: MatchRepository) {
    suspend fun getIndNzMatchDetails(): Result<INDNZMatchDetailsResponse> {
        Log.d("getIndNzMatchDetails", "getIndNzMatchDetails: ${matchRepository.getIndNzMatchDetails()}")
        return matchRepository.getIndNzMatchDetails()
    }

    suspend fun getSaPakMatchDetails(): Result<SAPAKMatchDetailsResponse> {
        Log.d("getSaPakMatchDetails", "getSaPakMatchDetails: ${matchRepository.getSaPakMatchDetails()}")
        return matchRepository.getSaPakMatchDetails()
    }
}