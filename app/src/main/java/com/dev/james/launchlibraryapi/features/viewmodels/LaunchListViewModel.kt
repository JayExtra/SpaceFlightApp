package com.dev.james.launchlibraryapi.features.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.james.launchlibraryapi.features.repositories.LaunchRepository
import com.dev.james.launchlibraryapi.models.Agency
import com.dev.james.launchlibraryapi.models.LaunchList
import com.dev.james.launchlibraryapi.models.OrbitRoom
import com.dev.james.launchlibraryapi.models.RocketInstance
import com.dev.james.launchlibraryapi.utils.Event
import com.dev.james.launchlibraryapi.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
   private val repository: LaunchRepository
) : ViewModel() {


    private val _agencyResponse : MutableLiveData<Event<NetworkResource<Agency>>> =
        MutableLiveData()
    val agencyResponse get() = _agencyResponse

    private val _rocketResponse : MutableLiveData<Event<NetworkResource<RocketInstance>>>
        = MutableLiveData()
    val rocketResponse get() = _rocketResponse

    private val _orbit : MutableLiveData<Event<OrbitRoom>> = MutableLiveData()
    val orbit get() = _orbit

    var fragmentId : Int? = null


    val launchListUpcoming =  repository.getLaunches(1).cachedIn(viewModelScope)

    val launchListPrevious = repository.getLaunches(0).cachedIn(viewModelScope)

    fun getAgency(id : Int ) = viewModelScope.launch {
        _agencyResponse.value = Event(NetworkResource.Loading)
        _agencyResponse.value = Event(repository.getAgency(id))
    }


    fun getRocket(id: Int) = viewModelScope.launch {
        _rocketResponse.value = Event(NetworkResource.Loading)
        _rocketResponse.value = Event(repository.getRocket(id))
    }


    fun getOrbit(id : Int) = viewModelScope.launch {
        try {
            val orbit = repository.getOrbit(id)
            _orbit.postValue(Event(orbit))
        }catch (e : Exception){
            Log.d("RoomFetchError", "getOrbit: ${e.toString()} ")
        }
    }

}

