package com.example.siassessment.domain

import java.io.Serializable

data class ErrorDetails(
    val msg: String,
    val code: Int,
    val errorType: ErrorType,
    val data: String? = null
) : Serializable