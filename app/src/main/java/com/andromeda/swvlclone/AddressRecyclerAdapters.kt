package com.andromeda.swvlclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_address.view.*


class AddressRecyclerAdapters(val currentLocationListener: (DeliveryLocation) -> Unit) : RecyclerView.Adapter<AddressRecyclerAdapters.ViewHolder>() {

    private var lastChecked: RadioButton? = null
    var currentLocation: DeliveryLocation? = null
    var deliveryLocations: List<DeliveryLocation> = ArrayList()

    fun set_CurrentLocation(place: DeliveryLocation) {
        currentLocation = place
        setChecked()
    }

    fun set_DeliveryLocations(deliveryLocations: List<DeliveryLocation>) {

        this.deliveryLocations = deliveryLocations
        setChecked()

    }

    override fun getItemViewType(position: Int): Int {

        val isPast = deliveryLocations.get(position).isCurrent
        val isSelected = deliveryLocations.get(position).isCurrent
        //categoriesPr.get(position).isInCart

        isPast?.let {
            if(isPast!!) {
                return R.layout.item_address
            } else {
                return R.layout.item_address
            }
        }

        return R.layout.item_address

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            R.layout.item_address -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false))
//            R.layout.item_address -> return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_current_location, parent, false))
            else -> { // Note the block
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_address,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {

        return deliveryLocations.size
    }


    fun setChecked() {

        deliveryLocations.let {
            currentLocation?.let {
                deliveryLocations.forEach {
                    it.isSelected = currentLocation!!.placeID == it.placeID
                }
            }
        }
        notifyDataSetChanged()


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val deliveryLocation_: DeliveryLocation = deliveryLocations.get(position)
//        if(!deliveryLocation_.isCurrent!!) {
//            holder.itemView.current_location_address.text = deliveryLocation_.name
//
//        }
        val isCurrent = deliveryLocation_.isCurrent
        holder.itemView.current_location_address_two.text = deliveryLocation_.address
//        holder.itemView.radiobutton.isChecked = deliveryLocation_.isSelected;
//        holder.itemView.radiobutton.tag = Integer.valueOf(position);


        holder.itemView.setOnClickListener {
            deliveryLocations.get(position).isSelected = true
            val currentLocation = DeliveryLocation( deliveryLocation_.placeID,
                deliveryLocation_.name,
                deliveryLocation_.address,
                deliveryLocation_.placeFullText, deliveryLocation_.latLng)
            notifyItemChanged(position)
            currentLocationListener(currentLocation)

        }

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        private var lastCheckedPos : Int = 0
    }

}