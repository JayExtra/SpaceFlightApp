package com.dev.james.launchlibraryapi.models

import com.google.gson.annotations.SerializedName

data class LaunchPadLocation(
    val id : String ,
    val url : String,
    val name : String,
    @SerializedName("country_code")
    val code : String,
    @SerializedName("map_image")
    val mapImage : String,
    @SerializedName("total_launch_count")
    val launchCount : Int,
    @SerializedName("total_landing_count")
    val landingCount : Int

)