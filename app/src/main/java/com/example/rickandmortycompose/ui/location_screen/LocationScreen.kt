package com.example.rickandmortycompose.ui.location_screen

import Screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    Image(
        painter = painterResource(id = R.drawable.bgr_rainbow),
        contentScale = ContentScale.FillBounds,
        contentDescription = "rick and morty bg",
        modifier = Modifier.fillMaxSize()
    )
    Loading(isLoading = loadingState.value)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
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
        columns = GridCells.Fixed(1),
        modifier
            .fillMaxSize()
//            .background(Color.Green)
            .padding(8.dp),
        contentPadding = PaddingValues(12.dp),
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
        .padding(15.dp)
        .fillMaxWidth()
        .clickable { onLocationClicked(location) }
//        .fillMaxHeight(0.25f)
        .padding(horizontal = 20.dp)
        .background(Color.Magenta.copy(alpha = 0.45f))
        .border(1.7.dp, Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Text(
            text = location.name,
            textAlign = TextAlign.Center,
            minLines = 1,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            color = Color.White,
            maxLines = 2,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        LocationTextFields(staticText = "Dimension", dynamicText = location.dimension)
        LocationTextFields(staticText = "Type", dynamicText = location.type)
        LocationTextFields(
            staticText = "Residents Count",
            dynamicText = location.residents.count().toString(),
            modifier = Modifier.padding(bottom = 15.dp)
        )

    }
}

@Composable
fun LocationTextFields(modifier: Modifier = Modifier, staticText: String, dynamicText: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$staticText:",
            textAlign = TextAlign.Start,
            minLines = 1,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = Color.Yellow,
            maxLines = 2,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
//                .fillMaxWidth()
                .padding(start = 10.dp)
        )
        Text(
            text = dynamicText,
            textAlign = TextAlign.Start,
            minLines = 1,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = Color.White,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
//                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LocationItemPreview() {
    val location = Location(
        "created", "dimension", 5, "name", listOf("1", "2", "3"), "type", "url"
    )
    RickAndMortyComposeTheme {
        Image(
            painter = painterResource(id = R.drawable.bgr_rainbow),
            contentScale = ContentScale.FillBounds,
            contentDescription = "rick and morty bg",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (a in 0..10) {
                LocationItem(location)
            }
        }
    }
}