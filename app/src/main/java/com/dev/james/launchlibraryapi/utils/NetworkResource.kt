package com.dev.james.launchlibraryapi.utils

import android.net.Network
import okhttp3.ResponseBody

sealed class NetworkResource<out T>{
    data class Success<out T>(val value: T) : NetworkResource<T>()
    data class Failure(
        val errorCode : Int?,
        val errBody : ResponseBody?
    ) : NetworkResource<Nothing>()
    object Loading : NetworkResource<Nothing>()
}
