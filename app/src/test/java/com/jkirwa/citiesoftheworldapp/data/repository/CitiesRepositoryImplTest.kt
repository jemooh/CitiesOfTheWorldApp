package com.jkirwa.citiesoftheworldapp.data.repository

import com.google.common.truth.Truth
import com.jkirwa.citiesoftheworldapp.data.citiesList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek

class CitiesRepositoryImplTest : Spek({

    val citiesRepository: CitiesRepository = mockk()

    val dispatcher = TestCoroutineDispatcher()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching local cities data from db") {

        test("Test that local Cities is fetched successfully") {
            runBlocking {
                coEvery {
                    citiesRepository.getCities()
                } returns flowOf(citiesList)
                val results = citiesRepository.getCities()
                Truth.assertThat(results).isNotNull()
            }
        }

        test("Test that local db search is successfully") {
            runBlocking {
                coEvery {
                    citiesRepository.searchCities("Rotterdam")
                } returns flowOf(citiesList)
                val results = citiesRepository.searchCities("Rotterdam")
                Truth.assertThat(results).isNotNull()
            }
        }
    }
})
