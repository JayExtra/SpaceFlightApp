package com.dev.james.launchlibraryapi.models

import com.google.gson.annotations.SerializedName

data class Launch(
    val count : Int ,
    val next : String?,
    val previous : String?,
    @SerializedName("results")
    val launchList : List<LaunchList>
)