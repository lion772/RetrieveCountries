package com.example.paisesapp.network

import com.example.paisesapp.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountriesApi(): Response<List<Country>>
}