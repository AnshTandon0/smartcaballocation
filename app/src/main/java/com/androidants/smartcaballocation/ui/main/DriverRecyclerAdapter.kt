package com.androidants.smartcaballocation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.databinding.DriverCardBinding

class DriverRecyclerAdapter(val list: List<Driver>, val context: Context , val status : Int ) :
    RecyclerView.Adapter<DriverRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DriverCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.phone.text = list[position].phone
        holder.distance.text = list[position].distance.toString() + " km away"
        if ( position == 0 && status == 0 )
            holder.cardView.visibility = View.VISIBLE

        if ( status == 1 )
        {
            holder.cardView.visibility = View.VISIBLE
            if ( list[position].status == 1 )
            {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                holder.text.text = "Busy"
            }
            else
                holder.text.text = "Free"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.number)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val text: TextView = itemView.findViewById(R.id.text)
        val cardView : CardView = itemView.findViewById(R.id.card_view)
    }
}