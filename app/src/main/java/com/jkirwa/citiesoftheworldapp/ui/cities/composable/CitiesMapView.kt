package com.jkirwa.citiesoftheworldapp.ui.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun WorldCitiesMapView() {
    val mapView = rememberMapViewWithLifecycle()
    val citiesViewModel = getViewModel<CitiesViewModel>()
    val uiState = citiesViewModel.state.collectAsState().value
    citiesViewModel.getAllCities()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AndroidView({ mapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true

                if (uiState.cities.isNotEmpty()) {
                    uiState.cities.forEach { cityView ->
                        val location = LatLng(cityView.lat ?: 0.0, cityView.lng ?: 0.0)
                        val markerOptions = MarkerOptions()
                            .title(cityView.cityName)
                            .snippet(
                                String.format(
                                    "A city in %s",
                                    cityView.countryName
                                )
                            )
                            .position(location)
                        map.addMarker(markerOptions)
                    }
                    val firstItem = uiState.cities[0]
                    val location2 = LatLng(firstItem.lat ?: 0.0, firstItem.lng ?: 0.0)
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 15.0f))
                }
            }
        }
    }
}
