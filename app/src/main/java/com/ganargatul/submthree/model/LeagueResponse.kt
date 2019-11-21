package com.ganargatul.submthree.model

import com.ganargatul.submthree.model.LeagueDetailsItem
import com.google.gson.annotations.SerializedName

data class LeagueResponse (
    @SerializedName("leagues")
    val leagues: List<LeagueDetailsItem>?
)