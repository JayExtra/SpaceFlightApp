package com.dev.james.launchlibraryapi.features.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.james.launchlibraryapi.features.repositories.LaunchRepository
import com.dev.james.launchlibraryapi.models.Agency
import com.dev.james.launchlibraryapi.models.LaunchList
import com.dev.james.launchlibraryapi.utils.Event
import com.dev.james.launchlibraryapi.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
   private val repository: LaunchRepository
) : ViewModel() {


    private val _agencyResponse : MutableLiveData<Event<NetworkResource<Agency>>> =
        MutableLiveData()
    val agencyResponse get() = _agencyResponse

    var fragmentId : Int? = null


    val launchListUpcoming =  repository.getLaunches(1).cachedIn(viewModelScope)

    val launchListPrevious = repository.getLaunches(0).cachedIn(viewModelScope)

    fun getAgency(id : Int ) = viewModelScope.launch {
        _agencyResponse.value = Event(NetworkResource.Loading)
        _agencyResponse.value = Event(repository.getAgency(id))
    }
}

