package com.example.siassessment.domain.response.base

data class ApiResponse<T>(
    val error: Boolean,
    val message: String,
    val data: T
)