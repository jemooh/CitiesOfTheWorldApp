package com.jkirwa.citiesoftheworldapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao : CoroutineBaseDao<City> {
    @Query("SELECT city.cityId,city.cityName,country.countryId,country.countryName,country.countryCode,city.lat,city.lng,city.createdAt,city.updatedAt FROM City city LEFT JOIN Country country ON city.countryId=country.countryId ORDER BY city.cityName ")
    fun getAllCities(): Flow<List<CityView>>


    @Query("SELECT city.cityId,city.cityName,country.countryId,country.countryName,country.countryCode,city.lat,city.lng,city.createdAt,city.updatedAt FROM City city LEFT JOIN Country country ON city.countryId=country.countryId WHERE city.cityName  LIKE :queryText ORDER BY city.cityName ")
    fun searchCities(queryText: String): Flow<List<CityView>>

}
