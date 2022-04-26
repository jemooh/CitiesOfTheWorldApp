package com.jkirwa.citiesoftheworldapp.viewmodel

import app.cash.turbine.test
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result
import com.jkirwa.citiesoftheworldapp.data.repository.CitiesRepository
import com.jkirwa.citiesoftheworldapp.data.testCitiesRemoteSearchResponse
import com.jkirwa.citiesoftheworldapp.data.testFetchCitiesResponse
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class CitiesViewModelTest : Spek({

    val citiesRepository = mockk<CitiesRepository>()
    val citiesViewModel by lazy { CitiesViewModel(citiesRepository = citiesRepository) }
    val page = 1
    val query = "ka"

    val dispatcher = TestCoroutineDispatcher()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Remote cities Information") {


        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery {
                    citiesRepository.fetchRemoteCities(
                        page = page
                    )
                } returns Result.Success(
                    true
                )
                citiesViewModel.fetchRemoteCities()
                coVerify { citiesRepository.fetchRemoteCities(page = page) }
                citiesViewModel.state.test {
                    awaitEvent()
                }
            }
        }

        test("Test that remote cities is fetched successfully") {

            runBlocking {
                coEvery {
                    citiesRepository.fetchRemoteCities(
                        page = page
                    )
                } returns Result.Success(
                    data = true
                )
                citiesViewModel.fetchRemoteCities()
                coVerify { citiesRepository.fetchRemoteCities(page = page) }
                citiesViewModel.state.test {
                    Assertions.assertEquals(awaitItem(), testFetchCitiesResponse)
                }
            }
        }
    }

    group("Test that search remote cities is successfully") {


        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery {
                    citiesRepository.searchRemoteCities(queryText = query, page = page)
                } returns Result.Success(
                    data = true
                )
                citiesViewModel.searchRemoteCities(query = query)
                coVerify { citiesRepository.searchRemoteCities(queryText = query, page = page) }
                citiesViewModel.state.test {
                    Assertions.assertEquals(awaitItem(), testCitiesRemoteSearchResponse)
                }
            }
        }

        test("Test that remote search is fetched successfully") {

            runBlocking {
                coEvery {
                    citiesRepository.searchRemoteCities(queryText = query, page = page)
                } returns Result.Success(
                    data = true
                )
                citiesViewModel.searchRemoteCities(query = query)
                coVerify { citiesRepository.searchRemoteCities(queryText = query, page = page) }
                citiesViewModel.state.test {
                    Assertions.assertEquals(awaitItem(), testCitiesRemoteSearchResponse)
                }
            }
        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})
