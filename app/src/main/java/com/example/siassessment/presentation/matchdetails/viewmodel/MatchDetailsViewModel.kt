package com.example.siassessment.presentation.matchdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.PlayerDetails
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.data.response.Teams
import com.example.siassessment.domain.CoroutinesDispatcherProvider
import com.example.siassessment.domain.response.base.Result
import com.example.siassessment.domain.usecase.MatchDetailsUseCase
import com.example.siassessment.presentation.states.Resource
import com.example.siassessment.presentation.states.ResourceState
import com.example.siassessment.utils.getErrorDetails
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
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

    private var _teamIndNz = MutableStateFlow<List<Teams>?>(null)
    val teamIndNz get() = _teamIndNz.asStateFlow()

    private var _teamSaPak = MutableStateFlow<List<Teams>?>(null)
    val teamSaPak get() = _teamSaPak.asStateFlow()


    fun getIndNzMatchDetails() = viewModelScope.launch {
        _indNzMatchDetails.value = Resource(ResourceState.LOADING)
        val result = withContext(dispatcher.io) {
            return@withContext matchDetailsUseCase.getIndNzMatchDetails()
        }
        when (result) {
            is Result.Success -> {
                parseMatchDetails(result.data)
            }

            is Result.Error -> {
                _indNzMatchDetails.value =
                    Resource(ResourceState.ERROR, error = result.exception.getErrorDetails())
            }
        }
    }

    fun getSaPakMatchDetails() = viewModelScope.launch(dispatcher.io) {
        _saPakMatchDetails.value = Resource(ResourceState.LOADING)
        val result = withContext(dispatcher.io) {
            return@withContext matchDetailsUseCase.getSaPakMatchDetails()
        }
        when (result) {
            is Result.Success -> {
                parseMatchDetails(result.data)
            }

            is Result.Error -> {
                _saPakMatchDetails.value =
                    Resource(ResourceState.ERROR, error = result.exception.getErrorDetails())
            }
        }
    }

    private fun parseMatchDetails(matchDetails: INDNZMatchDetailsResponse) {
        try {
            val teamsList = mutableListOf<Teams>()
            val teamsJson = JSONObject(Gson().toJson(matchDetails.teams))
            Log.d("parseMatchDetails", "teamsJson: $teamsJson")

            val teamKeys = teamsJson.keys()
            while (teamKeys.hasNext()) {
                val teamKey = teamKeys.next()
                val teamJsonObject = teamsJson.getJSONObject(teamKey)

                val teamName = teamJsonObject.getString("Name_Full")
                val playersJson = teamJsonObject.getJSONObject("Players")
                Log.d("parseMatchDetails", "playersJson: $playersJson")

                val players = playersJson.keys().asSequence().map { playerKey ->
                    val playerDetails = Gson().fromJson(
                        playersJson.getJSONObject(playerKey).toString(),
                        PlayerDetails::class.java
                    )
                    playerDetails // Ensure player has team name
                }.toList()

                teamsList.add(
                    Teams(
                        nameFull = teamName,
                        Players = players
                    )
                )
            }

            // Post the parsed team list to the LiveData
            _teamIndNz.value = teamsList
            _indNzMatchDetails.value = Resource(ResourceState.SUCCESS, data = matchDetails)

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle or log the error appropriately
        }
    }


    private fun parseMatchDetails(matchDetails: SAPAKMatchDetailsResponse) {

        try {
            val teamsList = mutableListOf<Teams>()
            val teamsJson = JSONObject(Gson().toJson(matchDetails.Teams))
            Log.d("parseMatchDetails", "teamsJson: $teamsJson")

            val teamKeys = teamsJson.keys()
            while (teamKeys.hasNext()) {
                val teamKey = teamKeys.next()
                val teamJsonObject = teamsJson.getJSONObject(teamKey)

                val teamName = teamJsonObject.getString("Name_Full")
                val playersJson = teamJsonObject.getJSONObject("Players")
                Log.d("parseMatchDetails", "playersJson: $playersJson")

                val players = playersJson.keys().asSequence().map { playerKey ->
                    val playerDetails = Gson().fromJson(
                        playersJson.getJSONObject(playerKey).toString(),
                        PlayerDetails::class.java
                    )
                    playerDetails // Ensure player has team name
                }.toList()

                teamsList.add(
                    Teams(
                        nameFull = teamName,
                        Players = players
                    )
                )
            }

            // Post the parsed team list to the LiveData
            _teamSaPak.value = teamsList
            _saPakMatchDetails.value = Resource(ResourceState.SUCCESS, data = matchDetails)

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle or log the error appropriately
        }
    }

}