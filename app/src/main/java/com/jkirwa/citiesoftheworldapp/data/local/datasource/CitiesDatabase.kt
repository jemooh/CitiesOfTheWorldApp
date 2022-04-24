package com.jkirwa.citiesoftheworldapp.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jkirwa.citiesoftheworldapp.data.local.dao.CitiesDao
import com.jkirwa.citiesoftheworldapp.data.local.dao.CountriesDao
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import com.jkirwa.citiesoftheworldapp.data.local.model.Country

@Database(
    entities = [City::class, Country::class],
    version = 1

)
@TypeConverters(Converters::class)
abstract class CitiesDatabase : RoomDatabase() {
    abstract val citiesDao: CitiesDao
    abstract val countriesDao: CountriesDao

    companion object {
        const val DATABASE_NAME = "cities_db"
    }
}
