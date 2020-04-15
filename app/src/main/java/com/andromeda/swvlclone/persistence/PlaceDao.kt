package com.andromeda.swvlclone.persistence

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.andromeda.swvlclone.DeliveryLocation

@Dao
interface DeliveryDao {

    @Query("SELECT * from current_location")
    fun getAllDeliveryLocations(): LiveData<List<DeliveryLocation>>

//    @Query("SELECT * from delivery_locations")
//    fun getAllOtherDeliveryLocations(): LiveData<List<DeliveryLocation>>

    @Query("DELETE FROM delivery_locations")
    suspend fun deleteAllDeliveryLocations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryLocation(place: DeliveryLocation)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertInAllDeliveryLocation(locations: DeliveryLocation)

//    @Query("DELETE FROM delivery_locations")
//    suspend fun deleteAllOtherDeliveryLocations()
}


class DeliveryRepository(private val deliveryDao: DeliveryDao) {

    val allDeliveryLocations: LiveData<List<DeliveryLocation>> = deliveryDao.getAllDeliveryLocations()
//    val allOtherDeliveryLocations: LiveData<List<DeliveryLocation>> = deliveryDao.getAllOtherDeliveryLocations()

    suspend fun insertCurrentDeliveryLocation(place: DeliveryLocation) {
        deliveryDao.insertDeliveryLocation(place = place)
    }

//    suspend fun insertInAllDeliveryLocation(locations: DeliveryLocation) {
//        deliveryDao.insertInAllDeliveryLocation(locations = locations)
//
//    }

    //    suspend fun deleteAllOtherDeliveryLocations() {
//        deliveryDao.deleteAllOtherDeliveryLocations()
//    }
    suspend fun deleteAllDeliveryLocations() {
        deliveryDao.deleteAllDeliveryLocations()
    }

}