package com.example.siassessment.data.remote.utils

import com.example.siassessment.domain.response.base.Result

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: Exception) {
        Result.Error(e)
    }
}