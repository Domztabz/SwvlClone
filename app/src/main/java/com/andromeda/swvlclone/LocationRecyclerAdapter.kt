package com.andromeda.swvlclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_location.view.*


data class Locations(val name: String, val pickUp: Boolean)
class LocationRecyclerAdapter(val items : List<Locations>, val context: Context, val clickListener: (Locations) -> Unit) : RecyclerView.Adapter<LocationRecyclerAdapterViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationRecyclerAdapterViewHolder {
        return LocationRecyclerAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_location, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: LocationRecyclerAdapterViewHolder, position: Int) {
        holder.name.text = items.get(position).name
        holder.itemView.setOnClickListener {
            clickListener(items.get(position))
        }
    }
}

class LocationRecyclerAdapterViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name = view.name
}