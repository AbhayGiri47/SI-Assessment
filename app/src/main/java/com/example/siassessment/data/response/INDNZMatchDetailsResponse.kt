package com.example.siassessment.data.response

import com.google.gson.annotations.SerializedName


data class INDNZMatchDetailsResponse(

    @SerializedName("Matchdetail") var matchDetail: MatchDetail? = MatchDetail(),
    @SerializedName("Teams") var teams: Any? = null

)

data class MatchDetail(
    @SerializedName("Match") var Match: Match? = Match(),
    @SerializedName("Series") var Series: Series? = Series(),
    @SerializedName("Venue") var Venue: Venue? = Venue()


)

data class Match(
    @SerializedName("Date") var Date: String? = null,
    @SerializedName("Time") var Time: String? = null

)

data class Series(
    @SerializedName("Tour_Name") var TourName: String? = null

)

data class Venue(
    @SerializedName("Name") var Name: String? = null
)

data class Teams(
    @SerializedName("Name_Full") var nameFull: String? = null,
    @SerializedName("Players") var Players: List<PlayerDetails>? = null
)

data class PlayerDetails(

    @SerializedName("Position") var position: String? = null,
    @SerializedName("Name_Full") var nameFull: String? = null,
    @SerializedName("Iskeeper") var isKeeper: Boolean? = null,
    @SerializedName("Iscaptain") var isCaptain: Boolean? = null,
    @SerializedName("Batting") var batting: Batting? = Batting(),
    @SerializedName("Bowling") var bowling: Bowling? = Bowling()

)

data class Batting(

    @SerializedName("Style") var style: String? = null,
    @SerializedName("Average") var average: String? = null,
    @SerializedName("Strikerate") var strikeRate: String? = null,
    @SerializedName("Runs") var runs: String? = null

)

data class Bowling(

    @SerializedName("Style") var style: String? = null,
    @SerializedName("Average") var average: String? = null,
    @SerializedName("Economyrate") var economyRate: String? = null,
    @SerializedName("Wickets") var wickets: String? = null

)