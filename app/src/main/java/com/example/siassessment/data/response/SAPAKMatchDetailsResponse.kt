package com.example.siassessment.data.response

import com.google.gson.annotations.SerializedName

data class SAPAKMatchDetailsResponse(

    @SerializedName("Matchdetail") var Matchdetail: MatchDetail? = MatchDetail(),
    @SerializedName("Teams") var Teams: Any? = null

    )
