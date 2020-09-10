package com.example.paisesapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paisesapp.R
import com.example.paisesapp.model.Country
import com.example.paisesapp.util.getProgressDrawable
import com.example.paisesapp.util.loadImage

class CountryListAdapter(private var countries: ArrayList<Country>):
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    var onClickCountry: ((Country)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(position)

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                onClickCountry?.invoke(countries[adapterPosition])
            }
        }

        fun bind(position: Int){
            val country = countries[position]
            val name = itemView.findViewById<TextView>(R.id.name)
            val capital = itemView.findViewById<TextView>(R.id.capital)
            val bandeira = itemView.findViewById<ImageView>(R.id.image_flag)
            val progressDrawable = getProgressDrawable(itemView.context)

            name.text = country.countryName
            capital.text = country.capital
            bandeira.loadImage(country.flag, progressDrawable)
        }

    }
}