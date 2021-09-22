package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceProvider(
    val id : Int,
    val url : String,
    val name : String,
    val type : String
) : Parcelable
