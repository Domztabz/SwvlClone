package com.andromeda.swvlclone

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andromeda.swvlclone.persistence.Place
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.activity_destinations.*

class DestinationsActivity : AppCompatActivity(), PlacesAutoCompleteAdapter.OnItemClickListener {
    override fun OnItemClick(place: Place?) {
        Log.d(TAG, "OnItemClick : $place")

        val address = "${place?.name}"
        to_edittext.setText(address)

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var AUTOCOMPLETE_REQUEST_CODE = 1
    val TAG = "EnterLocationAct"

    private var lastKnownLocation: Location? = null
    private lateinit var pickDeliveryLocationViewModel: PickDeliveryLocationViewModel
    private lateinit var latestPlace: Place
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLocationPermissionGranted : Boolean = false;
    private var locationRequestCode : Int = 1000;
    private lateinit var placesClient: PlacesClient
    private lateinit var addressRecyclerAdapters : AddressRecyclerAdapters
    private lateinit var resultReceiver: AddressResultReceiver
    private var currentLocation: Location? = null
    private var allLocations: ArrayList<DeliveryLocation> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        // Initialize place API
        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_maps_key));
        }
        placesClient = Places.createClient(this)
        addressRecyclerAdapters = AddressRecyclerAdapters({place: DeliveryLocation -> setCurrentLocation(place)})


        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        val token = AutocompleteSessionToken.newInstance()
//        to_edittext.setIconifiedByDefault(false)

        from?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                doFromSearch(p0.toString(), token)

            }

        })

        to_edittext?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                doSearch(p0.toString(), token)

            }

        })

        close_btn?.setOnClickListener {
            finish()
        }
//        to_edittext.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                av_view.show()
//                // Use the builder to create a FindAutocompletePredictionsRequest.
//                val request = FindAutocompletePredictionsRequest.builder()
//                    //.setLocationRestriction(bounds)
//                    .setCountry("KE")
//                    .setSessionToken(token)
//                    .setQuery(newText)
//                    .build()
//
//                placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
//
//                    val placesArrayList: ArrayList<Place> = ArrayList()
//                    for (prediction in response.autocompletePredictions) {
//
//                        prediction.placeId
//
//                        Log.d(TAG, "AutoComplete Predictions: $prediction." )
//
//                        Log.i(TAG, "Place ID: ${prediction.placeId}")
//
//                        val placeID : String = prediction.placeId
//
//                        Log.i(TAG, "Prediction: " + prediction)
//
//                        Log.i(TAG, "FullText: " + prediction.getFullText(null).toString())
//
//                        val fullText = prediction.getFullText(null).toString()
//                        val locations: List<String> = fullText.split(",")
//
//                        if(locations.size > 0) {
//                            val place = locations[0]
//                            Log.d(TAG, "Place: $place")
//                            val removedString = locations.drop(1)
//                            Log.d(TAG, "removedString: $removedString")
//
//                            val address = removedString.joinToString(", ")
//                            Log.d(TAG, "address: $address")
//
//                            val placeItem = Place(
//                                placeID,
//                                place,
//                                address,
//                                fullText
//                            )
//                            placesArrayList.add(placeItem)
//
//                        }
//
//                    }
//
//                    initializePlacesAutoCompleteRecycler(placesArrayList)
//                }.addOnFailureListener { exception ->
//                    if (exception is ApiException) {
//                        Log.e(TAG, "Place not found: " + exception.statusCode)
//                    }
//                }
//                return true
//            }
//
//        })
    }


    fun doSearch(newText : String, token: AutocompleteSessionToken) {
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request = FindAutocompletePredictionsRequest.builder()
            //.setLocationRestriction(bounds)
            .setCountry("KE")
            .setSessionToken(token)
            .setQuery(newText)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->

            val placesArrayList: ArrayList<Place> = ArrayList()
            for (prediction in response.autocompletePredictions) {

                prediction.placeId

                Log.d(TAG, "AutoComplete Predictions: $prediction." )

                Log.i(TAG, "Place ID: ${prediction.placeId}")

                val placeID : String = prediction.placeId

                Log.i(TAG, "Prediction: " + prediction)

                Log.i(TAG, "FullText: " + prediction.getFullText(null).toString())

                val fullText = prediction.getFullText(null).toString()
                val locations: List<String> = fullText.split(",")

                if(locations.size > 0) {
                    val place = locations[0]
                    Log.d(TAG, "Place: $place")
                    val removedString = locations.drop(1)
                    Log.d(TAG, "removedString: $removedString")

                    val address = removedString.joinToString(", ")
                    Log.d(TAG, "address: $address")

                    val placeItem = Place(
                        placeID,
                        place,
                        address,
                        fullText
                    )
                    placesArrayList.add(placeItem)

                }

            }

            initializePlacesAutoCompleteRecycler(placesArrayList)
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
            }
        }
    }

    fun doFromSearch(newText : String, token: AutocompleteSessionToken) {
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request = FindAutocompletePredictionsRequest.builder()
            //.setLocationRestriction(bounds)
            .setCountry("KE")
            .setSessionToken(token)
            .setQuery(newText)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->

            val placesArrayList: ArrayList<Place> = ArrayList()
            for (prediction in response.autocompletePredictions) {

                prediction.placeId

                Log.d(TAG, "AutoComplete Predictions: $prediction." )

                Log.i(TAG, "Place ID: ${prediction.placeId}")

                val placeID : String = prediction.placeId

                Log.i(TAG, "Prediction: " + prediction)

                Log.i(TAG, "FullText: " + prediction.getFullText(null).toString())

                val fullText = prediction.getFullText(null).toString()
                val locations: List<String> = fullText.split(",")

                if(locations.size > 0) {
                    val place = locations[0]
                    Log.d(TAG, "Place: $place")
                    val removedString = locations.drop(1)
                    Log.d(TAG, "removedString: $removedString")

                    val address = removedString.joinToString(", ")
                    Log.d(TAG, "address: $address")

                    val placeItem = Place(
                        placeID,
                        place,
                        address,
                        fullText
                    )
                    placesArrayList.add(placeItem)

                }

            }

            initializeFromPlacesAutoCompleteRecycler(placesArrayList)
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(TAG, "Place not found: " + exception.statusCode)
            }
        }
    }


    fun setCurrentLocation(place: DeliveryLocation) {

    }



    fun initializePlacesAutoCompleteRecycler(places : ArrayList<Place>) {
        to_locations_addresses.visibility = View.GONE
        from_recycler_items.visibility = View.VISIBLE
        val linearLayoutManager = LinearLayoutManager(this@DestinationsActivity)
        from_recycler_items.layoutManager = linearLayoutManager

        val ATTRS = intArrayOf(android.R.attr.listDivider)

//        val a = this.obtainStyledAttributes(ATTRS)
//        val divider = a.getDrawable(0)
//        val inset = resources.getDimensionPixelSize(R.dimen.locations)
//        val insetDivider = InsetDrawable(divider, inset, 0, 0, 0)
//        a.recycle()
//        val dividerItemDecoration = DividerItemDecoration(recycler_items.getContext(),
//                linearLayoutManager.orientation)
//        dividerItemDecoration.setDrawable(insetDivider)

//        recycler_items.addItemDecoration(dividerItemDecoration)

        val placesAutoCompleteAdapter = PlacesAutoCompleteAdapter(
            places,
            this,
            this@DestinationsActivity
        )

//        av_view.hide()

        from_recycler_items.adapter = placesAutoCompleteAdapter

    }

    fun initializeFromPlacesAutoCompleteRecycler(places : ArrayList<Place>) {
        from_recycler_items.visibility = View.GONE
        to_locations_addresses.visibility = View.VISIBLE
        val linearLayoutManager = LinearLayoutManager(this@DestinationsActivity)
        to_locations_addresses.layoutManager = linearLayoutManager

        val ATTRS = intArrayOf(android.R.attr.listDivider)

//        val a = this.obtainStyledAttributes(ATTRS)
//        val divider = a.getDrawable(0)
//        val inset = resources.getDimensionPixelSize(R.dimen.locations)
//        val insetDivider = InsetDrawable(divider, inset, 0, 0, 0)
//        a.recycle()
//        val dividerItemDecoration = DividerItemDecoration(recycler_items.getContext(),
//                linearLayoutManager.orientation)
//        dividerItemDecoration.setDrawable(insetDivider)

//        recycler_items.addItemDecoration(dividerItemDecoration)

        val placesAutoCompleteAdapter = PlacesAutoCompleteAdapter(
            places,
            this,
            {place: Place -> fromClickListener(place)}
        )

//        av_view.hide()


        to_locations_addresses.adapter = placesAutoCompleteAdapter

    }

    fun fromClickListener(place: Place) {

        Log.d(TAG, "fromClickListener : $place")

        val address = "${place?.name}"
        from.setText(address)

    }

//    fun retrievePlace(place_: Place) {
//        val placeFields : List<com.google.android.libraries.places.api.model.Place.Field>  = arrayListOf(com.google.android.libraries.places.api.model.Place.Field.LAT_LNG, com.google.android.libraries.places.api.model.Place.Field.ADDRESS_COMPONENTS)
//        placesClient.fetchPlace(FetchPlaceRequest.newInstance(place_.placeID,placeFields )).addOnSuccessListener {
//
//            val place = it.place
//            val deliveryLocation = DeliveryLocation(place_.placeID, place_.name, place_.address,place_.placeFullText, place.latLng.toString())
//            val currentDeliveryLocation = CurrentLocation(place_.placeID, place_.name, place_.address,place_.placeFullText, place.latLng.toString())
//            setNewCurrentLocInFirebase(deliveryLocation)
//
//
////            pickDeliveryLocationViewModel.deleteAllDeliveryLocations()
////            pickDeliveryLocationViewModel.insertCurrentDeliveryLocation(currentDeliveryLocation)
////            pickDeliveryLocationViewModel.insertDeliveryLocation(deliveryLocation)
//            Log.d(TAG, "Retrieved Place: $place")
//        }
//    }


    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {
//        private var mReceiver: ResultReceiver


        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            Log.d(TAG, "onReceiveResult called")

            // Display the address string
            // or an error message sent from the intent service.

            val addressOutput = resultData?.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY) ?: ""

//            address = addressOutput
//            TODO displayAddressOutput()
            Log.d(TAG, "Address Output in Fragment: $addressOutput")

            // Show a toast message if an address was found.
            if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {
//                showToast(getString(R.string.address_found))

                lastKnownLocation?.let {
                    //                    current_location_address_two?.text = addressOutput

                    val placeID = it.latitude.toString() + it.longitude.toString()
                    val latLng = LatLng(it.latitude, it.longitude)
                    currentLocation = it
                    val deliveryLocation = DeliveryLocation(placeID, addressOutput, addressOutput,addressOutput, latLng.toString(), true)
                    if(!allLocations.contains(deliveryLocation)) {
                        allLocations.add(0, deliveryLocation)
                        addressRecyclerAdapters.set_DeliveryLocations(allLocations)
                    }
                }
            }
        }
    }

}


@Entity(tableName = "delivery_locations")
data class DeliveryLocation(var placeID: String? = null,
                            var name: String? = null,
                            var address: String? = null,
                            var placeFullText: String? = null,
                            var latLng: String? = null,
                            var isCurrent: Boolean? = false,
                            var isSelected: Boolean = false )
@Entity(tableName = "current_location")
data class CurrentLocation(@NonNull @PrimaryKey var placeID: String,
                           var name: String? = null,
                           var address: String? = null,
                           var placeFullText: String? = null,
                           var latLng: String,
                           var isSelected: Boolean = false )