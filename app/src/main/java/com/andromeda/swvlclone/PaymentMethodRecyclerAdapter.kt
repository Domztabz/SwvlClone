package com.andromeda.swvlclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_payment_method.view.*

class PaymentMethodRecyclerAdapter(val items : List<PaymentMethod>, val context: Context, val clickListener: (PaymentMethod) -> Unit) : RecyclerView.Adapter<PaymentMethodRecyclerAdapterViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodRecyclerAdapterViewHolder {
        return PaymentMethodRecyclerAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment_method, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: PaymentMethodRecyclerAdapterViewHolder, position: Int) {
        Glide.with(context).load(items.get(position).link).into(holder.product_image)
        holder.category.text = items.get(position).name
        holder.itemView.setOnClickListener {
            clickListener(items.get(position))
        }
    }
}

class PaymentMethodRecyclerAdapterViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val product_image = view.image
    val category = view.name
}