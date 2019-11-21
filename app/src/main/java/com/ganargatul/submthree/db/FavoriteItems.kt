package com.ganargatul.submthree.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteItems(val ID_: Int?, val EVENT_ID_: Int?, val TEAM_HOME_: Int?, val TEAM_AWAY_: Int?, val EVENT_NAME_ : String?) : Parcelable {
    companion object{
        const val TABLE_FAVORITE: String = "FAVORITE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val TEAM_HOME_ID: String = "TEAM_HOME"
        const val TEAM_AWAY_ID: String = "TEAM_AWAY"
        const val EVENT_NAME : String = "EVENT_NAME"
    }
}