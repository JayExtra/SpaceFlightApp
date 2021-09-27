package com.dev.james.launchlibraryapi.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orbit_table")
data class OrbitRoom(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val description : String
): Parcelable