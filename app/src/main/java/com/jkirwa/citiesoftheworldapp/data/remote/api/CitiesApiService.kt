package com.jkirwa.citiesoftheworldapp.data.remote.api

import com.jkirwa.citiesoftheworldapp.data.remote.model.CitiesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CitiesApiService {

    @GET("city?include=country")
    suspend fun fetchRemoteCities(): Response<CitiesResponse>


    /* @GET("city?include=country")
     suspend fun fetchRemoteCities(
         @Query("page") page: Int
     ): Response<CitiesResponse>*/

}
