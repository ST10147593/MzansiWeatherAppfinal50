package com.example.mzansiweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.models.City

class CityAdapter(
    private val items: List<City>,
    private val onClick: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.cityNameTv)
        val tempTv: TextView = view.findViewById(R.id.cityTempTv)
        init {
            view.setOnClickListener { onClick(items[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val c = items[position]
        holder.nameTv.text = c.name
        holder.tempTv.text = "${c.currentTempC} °C"
    }

    override fun getItemCount(): Int = items.size
}
