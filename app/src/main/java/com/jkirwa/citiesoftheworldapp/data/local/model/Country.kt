package com.jkirwa.citiesoftheworldapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["countryId"], unique = true)])
data class Country(
    @PrimaryKey
    val countryId: Int = 0,
    val countryName: String?,
    val countryCode: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val continentId: Int?
)