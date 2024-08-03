package com.example.rickandmortycompose.ui.character_screen.character_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmortycompose.R
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.common.TextFields
import com.example.rickandmortycompose.utils.AsyncImageWithPreview

@Composable
fun CharacterDetailsScreen(modifier: Modifier = Modifier, characterId: String) {
    val viewModel: CharacterDetailsViewModel = hiltViewModel()
    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val characterState = viewModel.dataClass.character.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.getCharacter(characterId)
    }
    LaunchedEffect(key1 = errorState.value) {
        if (errorState.value.isNotEmpty()) {
            snackBarHostState.showSnackbar(errorState.value)
        }
    }
    Image(
        painter = painterResource(id = R.drawable.bg_pickle_ricks),
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
        characterState.value?.let {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CharacterDetailsItem(it)
            }
        }
    }
    ShowSnackBar(snackBarHostState = snackBarHostState, text = errorState.value)

}

@Composable
fun CharacterDetailsItem(character: Character) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(0.8f)
//            .fillMaxHeight()
            .background(Color.Red.copy(alpha = 0.3f))
            .border(1.dp, Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
//            .background(Color.Blue)
    ) {
        Box {
            AsyncImageWithPreview(
                model = character.image, contentDescription = character.name, modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .align(Alignment.CenterHorizontally)
                    .aspectRatio(1f)
            )
        }

        TextFields(
            staticText = "Name", dynamicText = character.name, dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Gender", dynamicText = character.gender, dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Status", dynamicText = character.status, dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Location", dynamicText = "location test", dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Origin",
            dynamicText = character.origin.name ?: "test",
            dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Type", dynamicText = character.type, dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Species", dynamicText = character.species, dynamicTextColor = Color.White
        )
        TextFields(
            staticText = "Episodes",
            dynamicText = character.episode.getEpisodes(),
            dynamicTextColor = Color.White,
            maxLines = 99
        )
    }

}

fun List<String>.getEpisodes(): String {
    return this.joinToString(",") { url -> url.substringAfterLast("/") }
}


