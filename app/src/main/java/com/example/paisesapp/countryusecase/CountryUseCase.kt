package com.example.paisesapp.countryusecase

import com.example.paisesapp.repository.CountryRepository

class CountryUseCase(private val countryRepository: CountryRepository) {

    suspend fun getCountries() = countryRepository.getCountries()
}