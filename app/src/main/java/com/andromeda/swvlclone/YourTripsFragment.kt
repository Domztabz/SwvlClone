package com.andromeda.swvlclone


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_your_trips.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YourTripsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class YourTripsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_trips, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

           (activity as NavigationActivity).setNavToolbarTitle("Trips")
        val trip = Trip("03/04/2020", "5:40PM", "Nakuru", "Nyayo Stadium", 300)
        val trips = ArrayList<Trip>()
        trips.add(trip)
        trips.add(trip)
        trips.add(trip)
        trips.add(trip)
        trips.add(trip)
        trips.add(trip)
        trips.add(trip)
        trips_recycler?.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = TripItemsAdapter(trips, requireContext(), {trip: Trip -> clickedTrip(trip) })
        }
    }

    fun clickedTrip(trip: Trip) {
        startActivity(Intent(requireContext(), TripsDetailActivity::class.java ))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YourTripsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            YourTripsFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
