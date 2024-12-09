package com.example.siassessment.data.response

import com.google.gson.annotations.SerializedName

data class SAPAKMatchDetailsResponse(

    @SerializedName("Matchdetail") var Matchdetail: Matchdetail? = Matchdetail(),
    @SerializedName("Teams") var Teams: TeamsSaPak? = TeamsSaPak()

    )

data class TeamsSaPak (
    @SerializedName("6" ) var team1 : Team? = Team(),
    @SerializedName("7" ) var team2: Team? = Team()
)