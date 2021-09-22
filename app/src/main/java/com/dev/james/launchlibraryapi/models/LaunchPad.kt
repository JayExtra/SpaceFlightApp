package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchPad(
    val id : Int,
    val url : String,
    val name : String,
    @SerializedName("map_url")
    val mapUrl : String,
    val latitude : String,
    val longitude : String,
    val location : LaunchPadLocation,
    @SerializedName("map_image")
    val mapImage : String,
    @SerializedName("total_launch_count")
    val launchCount : Int

):Parcelable
