package com.jkirwa.citiesoftheworldapp.ui.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun WorldCities() {
    val citiesViewModel = getViewModel<CitiesViewModel>()
    citiesViewModel.getCurrentWeather()
    val uiState = citiesViewModel.state.collectAsState().value
    if (uiState.cities.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                text = "Your world cities list is Empty"
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = uiState.cities,
                itemContent = {
                    CityListItem(cityView = it)
                }
            )
        }
    }
}

@Composable
private fun CityListItem(cityView: CityView) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 1.dp)
            .clickable { },
        elevation = 6.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        "City Name",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        cityView.cityName.toString(),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        "Country Name",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        cityView.countryName.toString(),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }

        }
    }
}
