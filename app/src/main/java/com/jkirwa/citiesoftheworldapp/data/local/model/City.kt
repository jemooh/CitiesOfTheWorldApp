package com.jkirwa.citiesoftheworldapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["cityId"], unique = true)])
data class City(
    @PrimaryKey
    val cityId: Int = 0,
    val cityName: String?,
    val countryId: Int?,
    val lat: Double?,
    val lng: Double?,
    val createdAt: String?,
    val updatedAt: String?,
    var currentPage: Int = 1
)