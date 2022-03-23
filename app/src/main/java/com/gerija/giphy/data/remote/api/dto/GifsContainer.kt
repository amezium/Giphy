package com.gerija.giphy.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class GifsContainer(
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
)