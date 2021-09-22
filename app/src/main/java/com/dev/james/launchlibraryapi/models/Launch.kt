package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Launch(
    val count : Int ,
    val next : String?,
    val previous : String?,
    @SerializedName("results")
    val launchList : List<LaunchList>
) : Parcelable