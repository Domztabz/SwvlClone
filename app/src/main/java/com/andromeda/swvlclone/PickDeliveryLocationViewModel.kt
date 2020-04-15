package com.andromeda.swvlclone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andromeda.swvlclone.persistence.Place

import kotlinx.coroutines.launch

class PickDeliveryLocationViewModel(application: Application) : AndroidViewModel(application) {


    val fromPlace = MutableLiveData<Place>()
    val toPlace = MutableLiveData<Place>()

    // The ViewModel maintains a reference to the repository to get data.
//    private val repository: DeliveryRepository
    // LiveData gives us updated words when they change.
//    val allDeliveryLocations: LiveData<List<CurrentLocation>>
//    val allOtherDeliveryLocations: LiveData<List<DeliveryLocation>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
//        // the correct WordRepository.
//        val deliveryDao = ImmicartRoomDatabase.getDatabase(application, viewModelScope).deliveryDao()
//        repository = DeliveryRepository(deliveryDao)
//        allDeliveryLocations = repository.allDeliveryLocations
//        allOtherDeliveryLocations = repository.allOtherDeliveryLocations
    }

    fun setFromPlace(place: Place) {
        fromPlace.value = place
    }

    fun setToPlace(place: Place) {
        toPlace.value = place
    }


//    fun allDeliveryLocations() : LiveData<List<CurrentLocation>> {
//        return allDeliveryLocations
//    }
////    fun allOtherDeliveryLocations() : LiveData<List<DeliveryLocation>> {
////        return allOtherDeliveryLocations
////    }
////
////    fun deleteAllOtherDeliveryLocations() = viewModelScope.launch {
////        repository.deleteAllOtherDeliveryLocations()
////    }
//
//
////    fun insertDeliveryLocation(deliveryLocation: DeliveryLocation) = viewModelScope.launch {
////        repository.insertInAllDeliveryLocation(deliveryLocation)
////    }
//
//    fun insertCurrentDeliveryLocation(place: CurrentLocation) = viewModelScope.launch {
//        repository.insertCurrentDeliveryLocation(place)
//    }
//
//    fun deleteAllDeliveryLocations() = viewModelScope.launch {
//        repository.deleteAllDeliveryLocations()
//    }

}