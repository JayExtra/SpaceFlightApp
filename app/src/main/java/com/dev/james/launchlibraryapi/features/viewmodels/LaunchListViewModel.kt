package com.dev.james.launchlibraryapi.features.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.james.launchlibraryapi.features.repositories.LaunchRepository
import com.dev.james.launchlibraryapi.models.LaunchList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
   private val repository: LaunchRepository
) : ViewModel() {


    var fragmentId : Int? = null


    val launchListUpcoming =  repository.getLaunches(1).cachedIn(viewModelScope)

    val launchListPrevious = repository.getLaunches(0).cachedIn(viewModelScope)


    companion object{
        const val UPCOMING_FRAGMENT_ID = 1
        const val DEFAULT_FRAGMENT_ID = 0
    }
}

