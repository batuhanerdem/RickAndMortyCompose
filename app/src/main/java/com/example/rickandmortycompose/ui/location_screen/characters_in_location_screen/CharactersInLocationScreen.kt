package com.example.rickandmortycompose.ui.location_screen.characters_in_location_screen

import Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.common.character.CharactersList

@Composable
fun CharactersInLocationScreen(
    modifier: Modifier = Modifier, navController: NavController, idList: List<String>
) {
    val viewModel: CharactersInLocationScreenViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val characterListState = viewModel.dataClass.characterList.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = errorState.value) {
        if (errorState.value.isNotEmpty()) {
            snackBarHostState.showSnackbar(errorState.value)
        }
    }

    LaunchedEffect(true) {
        viewModel.getCharacters(idList)
    }
    Loading(isLoading = loadingState.value)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 12.5.dp, end = 12.5.dp, top = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharactersList(characters = characterListState.value, onCharacterClicked = { character ->
            navController.navigate(Screens.CharacterDetails(character.id.toString()))
        })

    }

    ShowSnackBar(snackBarHostState = snackBarHostState, text = errorState.value)

}