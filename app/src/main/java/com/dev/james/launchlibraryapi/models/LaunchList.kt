package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class LaunchList(
    val id : String,
    val url : String,
    val slug : String,
    val name : String,
    val status : LaunchStatus?,
    @SerializedName("window_start")
    val date : String,
    @SerializedName("holdreason")
    val hold : String?,
    @SerializedName("failreason")
    val fail : String?,
    @SerializedName("launch_service_provider")
    val serviceProvider : ServiceProvider?,
    val rocket : Rocket?,
    val mission : Mission?,
    val pad : LaunchPad,
    val image : String,
    val probability : Int?
) : Parcelable{
    private val dateFormat : SimpleDateFormat
        get() =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    private val newDate : Date get() = dateFormat.parse(date)
    val launchDate : Date get() = dateFormat.parse(date)

    private val formatter : DateFormat
        get() =
        SimpleDateFormat("dd-MMM-yyy")

    val createdDateFormatted : String
        get() = formatter.format(newDate)
}
