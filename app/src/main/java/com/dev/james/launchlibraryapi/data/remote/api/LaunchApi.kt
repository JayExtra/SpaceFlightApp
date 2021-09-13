package com.dev.james.launchlibraryapi.data.remote.api

import com.dev.james.launchlibraryapi.models.Launch
import retrofit2.http.GET
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

    @GET("launch/upcoming")
    fun searchLaunchUpcoming(
        @Query("search") query : String?,
        @Query("limit") limit : Int?,
         @Query("offset") offset : Int?
    ) : Launch

    @GET("launch/previous")
    fun searchLaunchPrevious(
        @Query("search") query : String?,
        @Query("limit") limit : Int?,
        @Query("offset") offset : Int?
    ) : Launch
}