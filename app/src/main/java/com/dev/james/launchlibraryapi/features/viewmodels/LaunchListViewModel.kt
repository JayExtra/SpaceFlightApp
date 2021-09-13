package com.dev.james.launchlibraryapi.features.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.james.launchlibraryapi.features.repositories.LaunchRepository
import com.dev.james.launchlibraryapi.models.LaunchList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
   private val repository: LaunchRepository
) : ViewModel() {

    var launchListResult : Flow<PagingData<LaunchList>>? = null

    fun getLaunches(fragId : Int) : Flow<PagingData<LaunchList>>{
        val list : Flow<PagingData<LaunchList>> =
            repository.getLaunches(fragId)
                .cachedIn(viewModelScope)

        launchListResult = list
        return list
    }
}