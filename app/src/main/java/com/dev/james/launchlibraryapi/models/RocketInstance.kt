package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketInstance(
        val id : Int,
        val name : String,
        val description : String,
        val family : String,
        val manufacturer : Manufacturer,
        val variant : String,
        @SerializedName("max_stage")
        val maxStage : Int,
        @SerializedName("min_stage")
        val minStage : Int,
        val length : Float,
        val diameter : Float,
        @SerializedName("leo_capacity")
        val leoCapacity : Int,
        @SerializedName("gto_capacity")
        val gtoCapacity : Int,
        @SerializedName("to_thrust")
        val toThrust : Int,
        val apogee : Int,
        @SerializedName("image_url")
        val image : String,
        @SerializedName("total_launch_count")
        val totalLaunches : Int,
        @SerializedName("successful_launches")
        val successfulLaunches : Int,
        @SerializedName("failed_launches")
        val failedLaunches : Int

) : Parcelable