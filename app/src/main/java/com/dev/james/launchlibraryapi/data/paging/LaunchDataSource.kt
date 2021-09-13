package com.dev.james.launchlibraryapi.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.james.launchlibraryapi.data.remote.api.LaunchApi
import com.dev.james.launchlibraryapi.models.LaunchList
import com.dev.james.launchlibraryapi.utils.STARTING_OFFSET_INDEX
import java.io.IOException


class LaunchDataSource(
    private val launchApi : LaunchApi,
    private val fragId : Int
) : PagingSource<Int ,LaunchList >() {
    private val TAG = "LaunchDataSource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LaunchList> {
       val offset = params.key ?: STARTING_OFFSET_INDEX
        val limit = params.loadSize

        return try {
            if(fragId == 1){
                val launches = launchApi.getUpcomingLaunches(limit , offset)
                Log.d(TAG, "load: ${launches.toString()}")
                LoadResult.Page(
                    data = launches.launchList,
                    prevKey = if(offset == STARTING_OFFSET_INDEX) null else offset - limit,
                    nextKey = if(launches.next == null) null else offset + limit

                )
            }else{
                val launches = launchApi.getPreviousLaunches(limit , offset)
                Log.d(TAG, "load: ${launches.toString()}")
                LoadResult.Page(
                    data = launches.launchList,
                    prevKey = if(offset == STARTING_OFFSET_INDEX) null else offset - limit,
                    nextKey = if(launches.next == null) null else offset + limit

                )

            }

        }catch (t : Throwable){
            var excepton = t
            if(t is IOException){
                excepton = IOException("Check your imternet connection!")
            }
            LoadResult.Error(excepton)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LaunchList>): Int? {
        return state.anchorPosition
    }
}