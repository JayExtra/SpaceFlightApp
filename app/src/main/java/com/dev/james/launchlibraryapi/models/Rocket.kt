package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rocket(
    val id : Int,
    val configuration : RocketConfiguration?
)  :Parcelable