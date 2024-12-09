package com.example.siassessment.utils

import com.example.siassessment.domain.ErrorDetails
import com.example.siassessment.domain.ErrorType
import com.example.siassessment.domain.response.base.ApiResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

/**
 * Checks for [HttpException] and Code 400, parse and returns message
 * to be displayed to the user.
 */
fun Throwable.getErrorDetails(): ErrorDetails? {
    return try {
        if (this is HttpException && this.code() >= 400 && this.code() < 500) {
            val errResponse = this.response()?.errorBody()?.string()
            val apiResponse = Gson().fromJson<ApiResponse<Any>>(errResponse, ApiResponse::class.java)
            ErrorDetails(apiResponse.message, this.code(), ErrorType.HTTP_400, apiResponse?.data.toString())
        } else if (this is HttpException && this.code() >= 500 && this.code() < 600) {
            val errResponse = this.response()?.errorBody()?.string()
            val apiResponse =
                Gson().fromJson<ApiResponse<Any>>(errResponse, ApiResponse::class.java)
            ErrorDetails(apiResponse.message, this.code(), ErrorType.HTTP_500)
        } else if (this is IOException) {
            ErrorDetails("No Internet Connection", -1, ErrorType.NO_INTERNET_CONNECTION)
        } else {
            ErrorDetails(
                "Something went wrong. Please try again later.",
                -1,
                ErrorType.UNIDENTIFIED
            )
        }
    } catch (e: Exception) {
        ErrorDetails("Something went wrong. Please try again later.", -1, ErrorType.UNIDENTIFIED)
    }
}