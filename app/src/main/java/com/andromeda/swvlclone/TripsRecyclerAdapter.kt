package com.andromeda.swvlclone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.available_trips.view.*
import kotlinx.android.synthetic.main.item_trip.view.*
import java.text.DecimalFormat


data class Trip(val date: String, val eta : String, val start : String, val end : String, val amount : Int)
class TripItemsAdapter(val items : List<Trip>, val context: Context, val clickListener: (Trip) -> Unit) : RecyclerView.Adapter<TripItemsAdapterViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemsAdapterViewHolder {
        return TripItemsAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: TripItemsAdapterViewHolder, position: Int) {
        holder.bindItem(items.get(position))

        holder.itemView.setOnClickListener {
            clickListener(items.get(position))
        }
    }
}

class TripItemsAdapterViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val date = view.date
    val eta = view.eta
    val start = view.start
    val end = view.end
    val amount = view.amount
    fun bindItem(tripItem: Trip) {
        val formatter = DecimalFormat("#,###,###");
        val priceFormattedString = formatter.format(tripItem.amount.toInt());

        itemView.date.text = tripItem.date
        itemView.eta.text = tripItem.eta
        itemView.start.text = tripItem.start
        itemView.end.text = tripItem.end
        itemView.amount.text = "KES $priceFormattedString"


    }

}
data class AvailableTrips(val start : String, val end : String)
class AvailableTripItemsAdapter(val items : List<AvailableTrips>, val context: Context) : RecyclerView.Adapter<AvailableTripItemsAdapterViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTripItemsAdapterViewHolder {
        return AvailableTripItemsAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.available_trips, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: AvailableTripItemsAdapterViewHolder, position: Int) {
        holder.bindItem(items.get(position))
        holder.itemView.setOnClickListener {

            context.startActivity(Intent(context, SelectPickUpActivity::class.java))
        }
    }
}

class AvailableTripItemsAdapterViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    fun bindItem(tripItem: AvailableTrips) {


        itemView.available_end.text = tripItem.end
        itemView.available_start.text = tripItem.start

    }

}