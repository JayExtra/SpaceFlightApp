package com.dev.james.launchlibraryapi.features.repositories

import android.util.Log
import com.dev.james.launchlibraryapi.utils.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

open class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall : suspend() -> T
    ) : NetworkResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResource.Success(apiCall.invoke())
            } catch (exception : Exception){
                Log.d("ProblemHere", "safeApiCall: ${exception.toString()}")
                when(exception){
                    is HttpException -> {
                        NetworkResource.Failure(
                            exception.code(),
                            exception.response()?.errorBody()
                        )
                    } else -> {
                        NetworkResource.Failure(null , null)
                    }
                }
            }
        }
    }
}