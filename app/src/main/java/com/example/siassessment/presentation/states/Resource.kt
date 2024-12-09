package com.example.siassessment.presentation.states

import com.example.siassessment.domain.ErrorDetails

class Resource<T>(
    val status: ResourceState,
    val data: T? = null,
    val error: ErrorDetails? = null,
)
