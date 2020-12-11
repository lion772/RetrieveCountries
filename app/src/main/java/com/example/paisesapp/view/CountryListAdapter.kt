package com.example.paisesapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paisesapp.R
import com.example.paisesapp.databinding.ItemCountryBinding
import com.example.paisesapp.model.Country

class CountryListAdapter(private var countries: ArrayList<Country> = arrayListOf()):
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    var onClickCountry: ((Country)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(DataBindingUtil.inflate(inflater,
            R.layout.item_country, parent, false))
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(position)

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    inner class CountryViewHolder( var view: ItemCountryBinding): RecyclerView.ViewHolder(view.root), CountryClickListener{

        /*init {
            itemView.setOnClickListener{
                onClickCountry?.invoke(countries[adapterPosition])
            }
        }*/

        fun bind(position: Int){
            view.country = countries[position]
        }

        override fun onCountryClicked(v:View) {
            view.listener = this
        }
    }


}