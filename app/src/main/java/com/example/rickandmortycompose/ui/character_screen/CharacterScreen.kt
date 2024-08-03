package com.example.rickandmortycompose.ui.character_screen

import Screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.common.character.CharactersList
import com.example.rickandmortycompose.utils.AsyncImageWithPreview

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier, navController: NavController = rememberNavController()
) {
    val viewModel: CharacterScreenViewModel = hiltViewModel()
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
        viewModel.getAllCharacters()
    }

//    Image(
//        painter = painterResource(id = R.drawable.bg_character),
//        contentScale = ContentScale.FillBounds,
//        contentDescription = "rick and morty bg",
//        modifier = Modifier.fillMaxSize()
//    )
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

@Preview(showSystemUi = true)
@Composable
fun CharacterItemPreview() {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(0.45f)
            .fillMaxHeight(0.35f)
            .border(2.dp, Color.Black)
            .background(Color.Blue)
    ) {
        Box(modifier = Modifier) {
            AsyncImageWithPreview(
                model = null,
                contentDescription = "test isim",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .aspectRatio(1f)
                    .background(Color.Red)
            )
            Text(
                text = "test issadfasdim",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = TextUnit(19f, TextUnitType.Sp),

                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 15.dp)
//                    .background(Color.Gray)
            )
        }

    }
}

const val TAG = "tag"
