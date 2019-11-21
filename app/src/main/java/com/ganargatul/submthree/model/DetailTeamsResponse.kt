package com.ganargatul.submthree.model

import com.ganargatul.submthree.model.DetailTeamsItems
import com.google.gson.annotations.SerializedName

data class DetailTeamsResponse (
    @SerializedName("teams")
    val events: List<DetailTeamsItems>?
)