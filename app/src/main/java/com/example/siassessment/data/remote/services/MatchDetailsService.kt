package com.example.siassessment.data.remote.services

import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import retrofit2.http.GET

interface MatchDetailsService {

    @GET("nzin01312019187360.json")
    suspend fun getINDNZMatchDetails(): INDNZMatchDetailsResponse

    @GET("sapk01222019186652.json")
    suspend fun getSAPAKMatchDetails(): SAPAKMatchDetailsResponse

}