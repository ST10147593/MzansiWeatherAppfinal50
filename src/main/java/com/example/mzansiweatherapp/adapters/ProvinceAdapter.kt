package com.example.mzansiweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mzansiweatherapp.R
import com.example.mzansiweatherapp.models.Province

class ProvinceAdapter(
    private val items: List<Province>,
    private val onClick: (Province) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    inner class ProvinceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.provinceNameTv)
        init {
            view.setOnClickListener {
                onClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_province, parent, false)
        return ProvinceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.nameTv.text = items[position].name
    }

    override fun getItemCount(): Int = items.size
}
