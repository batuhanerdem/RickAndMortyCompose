package com.example.rickandmortycompose.ui.location_screen

import Screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortycompose.R
import com.example.rickandmortycompose.domain.model.Location
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.common.TextFields
import com.example.rickandmortycompose.ui.theme.Background
import com.example.rickandmortycompose.ui.theme.LightPortalGreen
import com.example.rickandmortycompose.ui.theme.RickAndMortyComposeTheme

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier, navController: NavController = rememberNavController()
) {

    val viewModel: LocationScreenViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val locationListState = viewModel.dataClass.locationList.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = errorState.value) {
        if (errorState.value.isNotEmpty()) {
            snackBarHostState.showSnackbar(errorState.value)
        }
    }

    LaunchedEffect(true) {
        viewModel.getAllLocations()
    }
    Loading(isLoading = loadingState.value)

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationList(locations = locationListState.value, onLocationClicked = { location ->
            navController.navigate(Screens.CharactersInLocation(location.residents))
        })

    }
    ShowSnackBar(snackBarHostState = snackBarHostState, text = errorState.value)

}

@Composable
fun LocationList(
    modifier: Modifier = Modifier,
    onLocationClicked: (Location) -> Unit = {},
    locations: List<Location>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(locations.toList(), key = Location::id) { location ->
            LocationItem(location, onLocationClicked)
        }
    }
}

@Composable
fun LocationItem(location: Location, onLocationClicked: (Location) -> Unit = {}) {
    Column(modifier = Modifier
        .fillMaxWidth(0.1f)
        .clickable { onLocationClicked(location) }
        .padding(horizontal = 5.dp)
        .border(1.2.dp, LightPortalGreen, shape = RoundedCornerShape(15.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 25.dp),
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_planet),
                contentDescription = "planet",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = location.name,
                textAlign = TextAlign.Start,
                minLines = 1,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                color = Color.Black,
                maxLines = 2,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            TextFields(text = "Dimension: ${location.dimension}")
            TextFields(text = "Type: ${location.type}")
            TextFields(
                text = "Residents Count: ${location.residents.count()}",
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LocationItemPreview() {
    var id = 0
    val location = Location(
        "created", "dimension", 0, "name", listOf("1", "2", "3"), "type", "url"
    )
    RickAndMortyComposeTheme {
        val locationList = mutableListOf(location)
        repeat(10) {
            locationList.add(location.copy(id = ++id))
        }

        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .background(Background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationList(locations = locationList.toList())

        }

    }
}