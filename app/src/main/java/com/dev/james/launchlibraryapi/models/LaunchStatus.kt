package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchStatus(
    val id : Int,
    val name : String,
    val abbrev : String,
    val description : String
):Parcelable
