package com.dev.james.launchlibraryapi.models

import com.google.gson.annotations.SerializedName

data class LaunchPad(
    val id : Int,
    val url : String,
    @SerializedName("map_url")
    val mapUrl : String,
    val latitude : String,
    val longitude : String,
    val location : LaunchPadLocation,
    @SerializedName("map_image")
    val mapImage : String,
    @SerializedName("total_launch_count")
    val launchCount : Int

)
