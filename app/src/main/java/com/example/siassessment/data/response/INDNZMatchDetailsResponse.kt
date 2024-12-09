package com.example.siassessment.data.response

import com.google.gson.annotations.SerializedName


data class INDNZMatchDetailsResponse(

    @SerializedName("Matchdetail") var Matchdetail: Matchdetail? = Matchdetail(),
    @SerializedName("Teams") var Teams: TeamINDNZ? = TeamINDNZ()

)

data class Matchdetail(

    @SerializedName("Team_Home") var TeamHome: String? = null,
    @SerializedName("Team_Away") var TeamAway: String? = null,
    @SerializedName("Match") var Match: Match? = Match(),
    @SerializedName("Series") var Series: Series? = Series(),
    @SerializedName("Venue") var Venue: Venue? = Venue(),
    @SerializedName("Weather") var Weather: String? = null,
    @SerializedName("Tosswonby") var Tosswonby: String? = null,
    @SerializedName("Status") var Status: String? = null,
    @SerializedName("Status_Id") var StatusId: String? = null,
    @SerializedName("Player_Match") var PlayerMatch: String? = null,
    @SerializedName("Result") var Result: String? = null,
    @SerializedName("Winningteam") var Winningteam: String? = null,
    @SerializedName("Winmargin") var Winmargin: String? = null,
    @SerializedName("Equation") var Equation: String? = null

)

data class Match(

    @SerializedName("Livecoverage") var Livecoverage: String? = null,
    @SerializedName("Id") var Id: String? = null,
    @SerializedName("Code") var Code: String? = null,
    @SerializedName("League") var League: String? = null,
    @SerializedName("Number") var Number: String? = null,
    @SerializedName("Type") var Type: String? = null,
    @SerializedName("Date") var Date: String? = null,
    @SerializedName("Time") var Time: String? = null,
    @SerializedName("Offset") var Offset: String? = null,
    @SerializedName("Daynight") var Daynight: String? = null

)

data class Series(

    @SerializedName("Id") var Id: String? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("Status") var Status: String? = null,
    @SerializedName("Tour") var Tour: String? = null,
    @SerializedName("Tour_Name") var TourName: String? = null

)

data class Venue(

    @SerializedName("Id") var Id: String? = null,
    @SerializedName("Name") var Name: String? = null

)

data class TeamINDNZ(

    @SerializedName("4") var team1: Team? = Team(),
    @SerializedName("5") var team2: Team? = Team()

)

data class Team(
    @SerializedName("Name_Full") var nameFull: String? = null,
    @SerializedName("Players") var Players: Any? = null
)

data class Players(

    var player1: PlayerDetails? = PlayerDetails(),
    var player2: PlayerDetails? = PlayerDetails(),
    var player3: PlayerDetails? = PlayerDetails(),
    var player4: PlayerDetails? = PlayerDetails(),
    var player5: PlayerDetails? = PlayerDetails(),
    var player6: PlayerDetails? = PlayerDetails(),
    var player7: PlayerDetails? = PlayerDetails(),
    var player8: PlayerDetails? = PlayerDetails(),
    var player9: PlayerDetails? = PlayerDetails(),
    var player10: PlayerDetails? = PlayerDetails(),
    var player11: PlayerDetails? = PlayerDetails()

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