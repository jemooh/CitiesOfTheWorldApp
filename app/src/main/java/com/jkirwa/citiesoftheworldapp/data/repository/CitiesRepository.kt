package com.jkirwa.citiesoftheworldapp.data.repository

import com.jkirwa.citiesoftheworldapp.data.local.model.City
import kotlinx.coroutines.flow.Flow
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result

interface CitiesRepository {
    suspend fun fetchRemoteCities(page: Int): Result<Boolean>
    fun getCities(): Flow<City>
}
