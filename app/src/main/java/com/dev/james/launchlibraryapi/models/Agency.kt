package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agency(
    val id : Int,
    val url : String ,
    val name : String,
    val abbrev : String,
    val type : String,
    @SerializedName("country_code")
    val code : String,
    @SerializedName("logo_url")
    val logo : String,
    @SerializedName("total_launch_count")
    val totalLaunch : Int,
    @SerializedName("successful_launches")
    val successfulLaunches : Int,
    @SerializedName("failed_launches")
    val failedLaunches : Int,
    @SerializedName("successful_landings")
    val successfulLandings : Int
    ):Parcelable