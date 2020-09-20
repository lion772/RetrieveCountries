package com.example.paisesapp.repository

import android.content.Context
import com.example.paisesapp.network.CountriesApi

class CountryRepository(private val context: Context, private val countryApi: CountriesApi): BaseRepository(){

    suspend fun getCountries() = safeCallApi{ countryApi.getCountriesApi() }
}