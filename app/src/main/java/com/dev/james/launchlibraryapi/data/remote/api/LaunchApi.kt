package com.dev.james.launchlibraryapi.data.remote.api

import com.dev.james.launchlibraryapi.models.Agency
import com.dev.james.launchlibraryapi.models.Launch
import com.dev.james.launchlibraryapi.models.RocketInstance
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LaunchApi {

    @GET("launch/previous")
    suspend fun getPreviousLaunches(
        @Query("limit") limit : Int?,
        @Query("offset") offset : Int?
    ) : Launch

    @GET("launch/upcoming")
    suspend fun getUpcomingLaunches(
        @Query("limit") limit : Int?,
        @Query("offset") offset : Int?
    ) : Launch

    @GET("agencies/{id}")
    suspend fun getAgency(
        @Path("id") id : Int
    ) : Agency

    @GET("config/launcher/{id}")
    suspend fun getRocketInstance(
        @Path("id") id : Int
    ) : RocketInstance

}