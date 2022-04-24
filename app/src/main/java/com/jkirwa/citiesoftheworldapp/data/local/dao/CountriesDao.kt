package com.jkirwa.citiesoftheworldapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jkirwa.citiesoftheworldapp.data.local.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao : CoroutineBaseDao<Country> {
    @Query("SELECT * FROM Country ")
    fun getAllCountries(): Flow<List<Country>>

}
