package com.example.rickandmortycompose.ui.character

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.SnackBar
import com.example.rickandmortycompose.utils.AsyncImageWithPreview

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier, navController: NavController = rememberNavController()
) {
    val viewModel: CharacterScreenViewModel = hiltViewModel()


    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val characterListState = viewModel.dataClass.characterList.collectAsStateWithLifecycle()
    Image(
        painter = painterResource(id = R.drawable.bgr_rainbow),
        contentScale = ContentScale.FillBounds,
        contentDescription = "rick and morty bg",
        modifier = Modifier.fillMaxSize()
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Loading(isLoading = loadingState.value)
        CharactersList(characters = characterListState.value, onCharacterClicked = { character ->
            navController.navigate("characterDetails/${character.id}")
        })
        SnackBar(modifier = Modifier, text = errorState.value)
    }
    LaunchedEffect(true) {
        viewModel.getAllCharacters()
    }

}


@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    onCharacterClicked: (Character) -> Unit = {},
    characters: List<Character>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier
            .fillMaxSize()
//            .background(Color.Green)
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(characters.toList(), key = Character::id) { character ->
            CharacterItem(character, onCharacterClicked)
        }
    }
}

@VisibleForTesting
@Composable
fun CharacterItem(character: Character, onCharacterClicked: (Character) -> Unit = {}) {
    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth()
        .clickable { onCharacterClicked(character) }
        .fillMaxHeight(0.25f)
        .border(1.dp, Color.White)
//            .background(Color.Blue)
    ) {
        AsyncImageWithPreview(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
        )

        Text(
            text = character.name,
            textAlign = TextAlign.Center,
            minLines = 2,
            color = Color.White,
            maxLines = 2,
            fontWeight = FontWeight.SemiBold,
            lineHeight = TextUnit(20f, TextUnitType.Sp),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 3.dp)
                .background(Color.Black.copy(alpha = 0.2f))
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}

@Preview
@Composable
fun CharacterItemPreview() {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.25f)
            .border(2.dp, Color.Black)
            .background(Color.Blue)
    ) {
        AsyncImageWithPreview(
            model = null,
            contentDescription = "test isim",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
                .background(Color.Red)
        )
        Text(
            text = "test isim",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 35.dp)
                .background(Color.Gray)
        )
    }
}

private fun characterOnClick(character: Character) {
    Log.d(TAG, "characterOnClick: ${character.id}")

}

const val TAG = "tag"
