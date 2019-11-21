package com.ganargatul.submthree.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PastEventItems (
    @SerializedName("idEvent")
    val idEvent: Int?,
    @SerializedName("strEvent")
    val strEvent:String?,
    @SerializedName("dateEvent")
    val dateEvent: String?,
    @SerializedName("strTime")
    val strTime: String?,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String?,
    @SerializedName("idAwayTeam")
    val idAwayTeam:String?,
    @SerializedName("strThumb")
    val strThumb: String?,
    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String?,
    @SerializedName("strHomeRedCards")
    val strHomeRedCards: String?,
    @SerializedName("strHomeYellowCards")
    val strHomeYellowCards: String?,
    @SerializedName("strAwayRedCards")
    val strAwayRedCards: String?,
    @SerializedName("strAwayYellowCards")
    val strAwayYellowCards: String?,
    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String?,
    @SerializedName("intHomeScore")
    val intHomeScore: Int?,
    @SerializedName("intAwayScore")
    val intAwayScore: Int?,
    @SerializedName("strSport")
    val strSport: String?
) : Parcelable