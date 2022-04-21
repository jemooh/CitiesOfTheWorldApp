package com.jkirwa.citiesoftheworldapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["id"], unique = true)])
data class City(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val countryId: Int?,
    val lat: Double?,
    val lng: Double?,
    val createdAt: String?,
    val updatedAt: String?
)