package com.ganargatul.submthree.model

import com.ganargatul.submthree.model.PastEventItems
import com.google.gson.annotations.SerializedName

data class PastEventReponse (
    @SerializedName("events")
    val events: List<PastEventItems>?
)