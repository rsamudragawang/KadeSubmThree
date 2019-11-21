package com.ganargatul.submthree.model

import com.google.gson.annotations.SerializedName

data class SearchEventResponse(
    @SerializedName("event")
    val event: List<PastEventItems>? = null
)