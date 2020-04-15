package com.andromeda.swvlclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_select_pick_up.*
import androidx.recyclerview.widget.DividerItemDecoration



class SelectPickUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pick_up)

        val locations = Locations("Christian Foundation Fellowship", false);
        val locations1 = Locations("Oil Libya Station", false);
        val locations2 = Locations("Kirigiti", false);
        val locations3 = Locations("Kist", false);
        val locations4 = Locations("Eden Ville", false);
        val locations5 = Locations("Bypass/Kwaheri", false);


        val locationsList = ArrayList<Locations>()
        locationsList.add(locations)
        locationsList.add(locations1)
        locationsList.add(locations2)
        locationsList.add(locations3)
        locationsList.add(locations4)
        locationsList.add(locations5)
        recycler_items?.let {
            val layoutManager = LinearLayoutManager(this)

            it.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(
                it.getContext(),
                layoutManager.getOrientation()
            )
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = LocationRecyclerAdapter(locationsList, this, {locations -> locationSelected(locations) })
        }


    }


    fun locationSelected(locations: Locations) {


        startActivity(Intent(this, SelectDropOffActivity::class.java))
    }
}
