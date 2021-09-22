package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketConfiguration(
    val id : Int,
    val url : String,
    val name : String,
    val family : String,
    val variant : String
):Parcelable
