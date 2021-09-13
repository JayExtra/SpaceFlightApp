package com.dev.james.launchlibraryapi.models

import com.google.gson.annotations.SerializedName

data class Mission(
    val id : Int,
    val name : String,
    val description : String?,
    @SerializedName("launch_designator")
    val designator : String?,
    val orbit : Orbit?

)
