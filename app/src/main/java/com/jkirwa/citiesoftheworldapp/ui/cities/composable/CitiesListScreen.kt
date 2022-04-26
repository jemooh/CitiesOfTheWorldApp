package com.jkirwa.citiesoftheworldapp.ui.cities.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkirwa.citiesoftheworldapp.R
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.PAGE_SIZE
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun WorldCitiesListScreen() {
    val citiesViewModel = getViewModel<CitiesViewModel>()
    val uiState = citiesViewModel.state.collectAsState().value
    val loading = uiState.isLoadingCities
    val page = uiState.page
    citiesViewModel.getAllCities()
    citiesViewModel.getCurrentPage()

    var showClearIcon by remember { mutableStateOf(false) }


    if (uiState.query.isEmpty()) {
        showClearIcon = false
        uiState.isSearchingCities = false
        citiesViewModel.getAllCities()
    } else if (uiState.query.isNotEmpty()) {
        showClearIcon = true
        uiState.isSearchingCities = true
    }

    if (!uiState.isSearchingCities && uiState.cities.isEmpty() && uiState.query.isEmpty()) {
        ShowProgressBar(textMessage = stringResource(R.string.fetch_message))
    } else {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            val textState = remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                value = textState.value,
                onValueChange = { onQueryChanged ->
                    textState.value = onQueryChanged
                    citiesViewModel.state.value.query = onQueryChanged
                    if (onQueryChanged.isNotEmpty()) {
                        citiesViewModel.searchCities(onQueryChanged)
                    } else {
                        citiesViewModel.getAllCities()
                    }

                },
                label = { Text(text = stringResource(id = R.string.search_place_holder_text)) },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Search icon"
                    )
                },
                trailingIcon = {
                    if (showClearIcon) {
                        IconButton(onClick = {
                            textState.value = ""
                            uiState.query = ""
                            citiesViewModel.getAllCities()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = "Clear icon"
                            )
                        }
                    }
                }
            )

            if (uiState.isSearchingCities && uiState.cities.isEmpty()) {
                ShowProgressBar(textMessage = stringResource(R.string.search_message))
            }

            if (!uiState.isSearchingCities && uiState.cities.isEmpty() && uiState.query.isNotEmpty()) {
                ShowEmptyScreen(textMessage = stringResource(R.string.search_not_found_message))
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(
                    items = uiState.cities
                ) { index, cityView ->
                    citiesViewModel.onChangeCitiesScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        citiesViewModel.nextPage()
                    }
                    CityListItem(cityView = cityView)
                }

                item {
                    if (uiState.isLoadingCities) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
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


@Composable
private fun ShowProgressBar(textMessage: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            CircularProgressIndicator()
        }
        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            text = textMessage
        )
    }
}


@Composable
private fun ShowEmptyScreen(textMessage: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            text = textMessage
        )
    }
}
