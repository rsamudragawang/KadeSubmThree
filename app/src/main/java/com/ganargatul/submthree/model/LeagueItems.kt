package com.ganargatul.submthree.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueItems (val name: String?,
                        val description: String?,
                        val image: Int?,val id:Int?) : Parcelable