package com.androidants.smartcaballocation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidants.smartcaballocation.R
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.databinding.DriverCardBinding
import kotlin.math.pow
import kotlin.math.sqrt

class DriverRecyclerAdapter(val list: List<Driver>, val context: Context , val lat1: Float , val lon1: Float) :
    RecyclerView.Adapter<DriverRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DriverCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.phone.text = list[position].phone
        holder.distance.text = calculateDistance(lat1 , lon1 , list[position].latitute , list[position].longitude)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.number)
        val distance: TextView = itemView.findViewById(R.id.distance)
    }

    private fun calculateDistance(lat1: Float, lat2: Float, lon1: Float, lon2: Float) : String {
        val lat = (lat2-lat1).pow(2)
        val lon = (lon1-lon2).pow(2)
        return sqrt(lat+lon).toString()
    }
}