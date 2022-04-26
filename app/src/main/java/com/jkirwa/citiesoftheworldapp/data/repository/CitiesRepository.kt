package com.jkirwa.citiesoftheworldapp.data.repository

import com.jkirwa.citiesoftheworldapp.data.remote.model.Result
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    suspend fun fetchRemoteCities(page: Int): Result<Boolean>
    suspend fun searchRemoteCities(queryText: String, page: Int): Result<Boolean>
    fun getCities(): Flow<List<CityView>>
    fun searchCities(queryText: String): Flow<List<CityView>>
    fun getCurrentPage(): Flow<Int?>?
}
