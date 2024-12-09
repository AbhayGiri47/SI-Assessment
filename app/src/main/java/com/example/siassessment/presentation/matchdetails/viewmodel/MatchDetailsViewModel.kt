package com.example.siassessment.presentation.matchdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.domain.CoroutinesDispatcherProvider
import com.example.siassessment.domain.response.base.Result
import com.example.siassessment.domain.usecase.MatchDetailsUseCase
import com.example.siassessment.presentation.states.Resource
import com.example.siassessment.utils.getErrorDetails
import com.example.siassessment.presentation.states.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider,
    private val matchDetailsUseCase: MatchDetailsUseCase
) : ViewModel() {

    private val _indNzMatchDetails =
        MutableStateFlow<Resource<INDNZMatchDetailsResponse>?>(null)
    val indNzMatchDetails get() = _indNzMatchDetails.asStateFlow()

    private val _saPakMatchDetails =
        MutableStateFlow<Resource<SAPAKMatchDetailsResponse>?>(null)
    val saPakMatchDetails get() = _saPakMatchDetails.asStateFlow()

    init {
        getIndNzMatchDetails()
        getSaPakMatchDetails()
    }

    private fun getIndNzMatchDetails() = viewModelScope.launch {
        _indNzMatchDetails.value = Resource(ResourceState.LOADING)
        val result = withContext(dispatcher.io) {
            return@withContext matchDetailsUseCase.getIndNzMatchDetails()
        }
        when (result) {
            is Result.Success -> {
                _indNzMatchDetails.value = Resource(ResourceState.SUCCESS, data = result.data)
            }

            is Result.Error -> {
                _indNzMatchDetails.value =
                    Resource(ResourceState.ERROR, error = result.exception.getErrorDetails())
            }
        }
    }

    private fun getSaPakMatchDetails() = viewModelScope.launch(dispatcher.io) {
        _saPakMatchDetails.value = Resource(ResourceState.LOADING)
        val result = withContext(dispatcher.io) {
            return@withContext matchDetailsUseCase.getSaPakMatchDetails()
        }
        when (result) {
            is Result.Success -> {
                _saPakMatchDetails.value = Resource(ResourceState.SUCCESS, data = result.data)
            }

            is Result.Error -> {
                _saPakMatchDetails.value =
                    Resource(ResourceState.ERROR, error = result.exception.getErrorDetails())
            }
        }
    }

}