package com.example.rickandmortycompose.ui.all_characters_screen.character_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.domain.model.CharactersLocation
import com.example.rickandmortycompose.domain.model.Origin
import com.example.rickandmortycompose.ui.common.CharacterDetailText
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar

@Composable
fun CharacterDetailsScreen(characterId: String) {
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
    Loading(isLoading = loadingState.value)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        characterState.value?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
//                    .background(Color.Black.copy(alpha = 0.05f))
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                text = character.name,
                lineHeight = TextUnit(40f, TextUnitType.Sp),
                color = Color.White,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                fontSize = TextUnit(35f, TextUnitType.Sp)
            )
        }
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp)) {
            CharacterDetailText(text = "Gender: ${character.gender.ifEmpty()}")
            CharacterDetailText(text = "Status: ${character.status.ifEmpty()}")
            CharacterDetailText(
                text = "Location: ${character.location.name}",
            )
            CharacterDetailText(text = "Origin: ${character.origin.name.ifEmpty()}")
            CharacterDetailText(text = "Type: ${character.type.ifEmpty()}")
            CharacterDetailText(text = "Species: ${character.species.ifEmpty()}")
            CharacterDetailText(
                text = "Episodes: ${character.episode.getEpisodes()}",
                textColor = Color.Black,
                maxLines = 99
            )
        }
    }
}

private fun List<String>.getEpisodes(): String {
    return this.joinToString(",") { url -> url.substringAfterLast("/") }
}

private fun String.ifEmpty(): String {
    return this.ifEmpty { "-" }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterDetailsScreenPreview() {
    val sampleCharacter = Character(
        id = 1,
        name = "Rick Sanchez Long Nickname ",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Origin("Earth (C-137)", "url"),
        location = CharactersLocation("Earth (Replacement Dimension)", "url"),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episode = listOf(
            "https://rickandmortyapi.com/api/episode/1", "https://rickandmortyapi.com/api/episode/2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,34,34,34,34,34,34,34,34,34,3,43,43,"
        ),
        created = "",
        url = ""
    )
    CharacterDetailsItem(sampleCharacter)
}