package com.example.siassessment.presentation.playerdetails.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.PlayerDetails
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.data.response.Team
import com.example.siassessment.data.response.Teams
import com.example.siassessment.domain.CoroutinesDispatcherProvider
import com.example.siassessment.domain.response.base.Result
import com.example.siassessment.domain.usecase.MatchDetailsUseCase
import com.example.siassessment.presentation.states.Resource
import com.example.siassessment.utils.getErrorDetails
import com.google.gson.Gson
import com.example.siassessment.presentation.states.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class PlayerDetailsViewModel @Inject constructor(
    private val dispatcher: CoroutinesDispatcherProvider,
    private val matchDetailsUseCase: MatchDetailsUseCase
) : ViewModel() {

    private val _indNzMatchDetails =
        MutableStateFlow<Resource<INDNZMatchDetailsResponse>?>(null)
    val indNzMatchDetails get() = _indNzMatchDetails.asStateFlow()

    private val _saPakMatchDetails =
        MutableStateFlow<Resource<SAPAKMatchDetailsResponse>?>(null)
    val saPakMatchDetails get() = _saPakMatchDetails.asStateFlow()

    private var _team1 = MutableLiveData<List<Teams>>()
    val team1: LiveData<List<Teams>> = _team1

    private var _team2 = MutableLiveData<List<Teams>>()
    val team2: LiveData<List<Teams>> = _team2


     fun getIndNzMatchDetails() = viewModelScope.launch {
        _indNzMatchDetails.value = Resource(ResourceState.LOADING)
        val result = withContext(dispatcher.io) {
            return@withContext matchDetailsUseCase.getIndNzMatchDetails()
        }
        when (result) {
            is Result.Success -> {
                _indNzMatchDetails.value = Resource(ResourceState.SUCCESS, data = result.data)
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
                _saPakMatchDetails.value = Resource(ResourceState.SUCCESS, data = result.data)
                parseMatchDetails(result.data)
            }

            is Result.Error -> {
                _saPakMatchDetails.value =
                    Resource(ResourceState.ERROR, error = result.exception.getErrorDetails())
            }
        }
    }

//    private fun parseMatchDetails(matchDetails: INDNZMatchDetailsResponse) {
//        val temp1Teams = mutableListOf<Teams>()
//        val teamListJSONObject = JSONObject(Gson().toJson(matchDetails.Teams))
//
//        val teamKeys: Iterator<String> = teamListJSONObject.keys()
//        while (teamKeys.hasNext()) {
//            val teamKey = teamKeys.next()
//            val teamJsonObject =
//                JSONObject(teamListJSONObject.getJSONObject(teamKey).toString())
//            val players = mutableListOf<PlayerDetails>()
//            val playersListJsonObject = teamJsonObject.getJSONObject("Players")
//            val playerKeys: Iterator<String> = playersListJsonObject.keys()
//            while (playerKeys.hasNext()) {
//                val playerKey = playerKeys.next()
//                val player = Gson().fromJson(
//                    playersListJsonObject.getJSONObject(playerKey).toString(),
//                    PlayerDetails::class.java
//                )
//                players.add(player.copy(nameFull = teamJsonObject.getString("Name_Full")))
//            }
//            temp1Teams.add(
//                Teams(
//                    nameFull = teamJsonObject.getString("Name_Full"),
//                    Players = players
//                )
//            )
//        }
//        _team1.postValue(temp1Teams)
//    }

    private fun parseMatchDetails(matchDetails: INDNZMatchDetailsResponse) {
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
            _team1.postValue(teamsList)

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle or log the error appropriately
        }
    }


    private fun parseMatchDetails(matchDetails: SAPAKMatchDetailsResponse) {
        val temp2Teams = mutableListOf<Teams>()
        val teamListJSONObject = JSONObject(Gson().toJson(matchDetails.Teams))

        val teamKeys: Iterator<String> = teamListJSONObject.keys()
        while (teamKeys.hasNext()) {
            val teamKey = teamKeys.next()
            val teamJsonObject =
                JSONObject(teamListJSONObject.getJSONObject(teamKey).toString())
            val players = mutableListOf<PlayerDetails>()
            val playersListJsonObject = teamJsonObject.getJSONObject("Players")
            val playerKeys: Iterator<String> = playersListJsonObject.keys()
            while (playerKeys.hasNext()) {
                val playerKey = playerKeys.next()
                val player = Gson().fromJson(
                    playersListJsonObject.getJSONObject(playerKey).toString(),
                    PlayerDetails::class.java
                )
                players.add(player.copy(nameFull = teamJsonObject.getString("Name_Full")))
            }
            temp2Teams.add(
                Teams(
                    nameFull = teamJsonObject.getString("Name_Full"),
                    Players = players
                )
            )
        }

        _team2.postValue(temp2Teams)
    }

}