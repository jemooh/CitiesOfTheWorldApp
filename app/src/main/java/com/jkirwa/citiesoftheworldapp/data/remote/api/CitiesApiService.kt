package com.jkirwa.citiesoftheworldapp.data.remote.api

import com.jkirwa.citiesoftheworldapp.data.remote.model.CitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApiService {

    @GET("city")
    suspend fun fetchRemoteCities(
        @Query("page") page: Int,
        @Query("include") include: String
    ): Response<CitiesResponse>


    @GET("city")
    suspend fun searchRemoteCities(
        @Query("filter[0][name][contains]=") queryText: String,
        @Query("page") page: Int,
        @Query("include") include: String
    ): Response<CitiesResponse>

}
