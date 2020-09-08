package com.example.paisesapp.di

import com.example.paisesapp.model.CountriesService
import com.example.paisesapp.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)
    fun injectCountryService(listViewModel: ListViewModel)

}