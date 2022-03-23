package com.gerija.giphy.data.remote.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Original(
    @SerializedName("url") var url: String? = null
) : Serializable