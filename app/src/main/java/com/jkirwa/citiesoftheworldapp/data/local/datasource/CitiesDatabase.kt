package com.jkirwa.citiesoftheworldapp.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jkirwa.citiesoftheworldapp.data.local.dao.CitiesDao
import com.jkirwa.citiesoftheworldapp.data.local.datasource.Converters
import com.jkirwa.citiesoftheworldapp.data.local.model.City

@Database(
    entities = [City::class],
    version = 1

)
@TypeConverters(Converters::class)
abstract class CitiesDatabase : RoomDatabase() {
    abstract val citiesDao: CitiesDao

    companion object {
        const val DATABASE_NAME = "cities_db"
    }
}
