package com.jkirwa.citiesoftheworldapp.data.local.dao

import androidx.room.*
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao : CoroutineBaseDao<City> {
    @Query("SELECT * FROM City ")
    fun getAllCities(): Flow<City>

}
