package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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

):Parcelable