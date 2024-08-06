package com.example.rickandmortycompose.ui.episode_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortycompose.R
import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.theme.Golden
import com.example.rickandmortycompose.ui.theme.EspressoBrown
import com.example.rickandmortycompose.ui.theme.RickAndMortyComposeTheme

@Composable
fun EpisodeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    episodeIdPair: Pair<Int, Int>
) {

    val viewModel: EpisodeScreenViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val episodeListState = viewModel.dataClass.episodeList.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = errorState.value) {
        if (errorState.value.isEmpty()) return@LaunchedEffect
        snackBarHostState.showSnackbar(errorState.value)

    }
    LaunchedEffect(Unit) {
        viewModel.getEpisodes(episodeIdPair)
    }
    Loading(isLoading = loadingState.value)

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(episodeListState.value, key = { _, episode -> episode.id }) { index, episode ->
            EpisodeItem(episode = episode, count = index + 1)
        }
    }
    ShowSnackBar(snackBarHostState = snackBarHostState, text = errorState.value)

}


@Composable
fun EpisodeItem(modifier: Modifier = Modifier, episode: Episode, count: Int) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .fillMaxHeight(0.2f)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$count.",
            color = Color.Black,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.rick),
            contentDescription = "rick",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(70.dp)
                .aspectRatio(1f)
        )
        NameAndCount(
            name = episode.name,
            characterCount = episode.characters.count().toString(),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = 10.dp)
        )
        Text(
            text = episode.airDate,
            color = EspressoBrown,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(16f, TextUnitType.Sp)
        )

    }

}

@Composable
fun NameAndCount(modifier: Modifier = Modifier, name: String, characterCount: String) {
    Column(modifier = modifier) {
        Text(
            text = name,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontSize = TextUnit(16f, TextUnitType.Sp)
        )
        Text(
            text = "Character count: $characterCount",
            color = Golden,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(14f, TextUnitType.Sp)
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EpisodeItemPreview() {

    RickAndMortyComposeTheme {
        LazyColumn(
            modifier = Modifier.padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}