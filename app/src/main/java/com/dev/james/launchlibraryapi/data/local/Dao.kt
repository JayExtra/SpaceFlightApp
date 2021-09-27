package com.dev.james.launchlibraryapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.james.launchlibraryapi.models.OrbitRoom

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun addOrbit(orbitRoom: OrbitRoom)

    @Query("SELECT * FROM orbit_table WHERE id = :orbitId")
    suspend fun getOrbit(orbitId : Int) : OrbitRoom
}