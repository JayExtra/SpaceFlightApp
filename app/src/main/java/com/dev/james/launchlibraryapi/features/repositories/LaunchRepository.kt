package com.dev.james.launchlibraryapi.features.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dev.james.launchlibraryapi.data.paging.LaunchDataSource
import com.dev.james.launchlibraryapi.data.remote.api.LaunchApi
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val api : LaunchApi
) : BaseRepository(){

    /**
     * returns list of launches either upcoming or previous
    from the paging source
     **/

    fun getLaunches( fragId : Int) = Pager(
        config = PagingConfig(enablePlaceholders = false , pageSize = 50),
        pagingSourceFactory = {
            LaunchDataSource(api , fragId)
        }
    ).flow

    /**
     * This will an agency instance for the launch details fragment
     * **/

    suspend fun getAgency(id : Int) = safeApiCall {
        api.getAgency(id)
    }
}