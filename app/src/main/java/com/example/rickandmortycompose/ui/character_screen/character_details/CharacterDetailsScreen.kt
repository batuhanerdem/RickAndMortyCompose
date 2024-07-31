package com.example.rickandmortycompose.ui.character_screen.character_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.SnackBar
import com.example.rickandmortycompose.utils.AsyncImageWithPreview

@Composable
fun CharacterDetailsScreen(modifier: Modifier = Modifier, characterId: String) {
    val viewModel: CharacterDetailsViewModel = hiltViewModel()
    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val characterState = viewModel.dataClass.character.collectAsStateWithLifecycle()


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Loading(isLoading = loadingState.value)
        characterState.value?.let {
            Character(it)
        }
        SnackBar(modifier = Modifier, text = errorState.value)
    }
    LaunchedEffect(true) {
        viewModel.getCharacter(characterId)
    }
}

@Composable
fun Character(character: Character) {
    Column(modifier = Modifier
        .padding(15.dp)
        .fillMaxWidth(0.8f)
        .fillMaxHeight(0.5f)
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
