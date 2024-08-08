package com.example.rickandmortycompose.ui.season_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortycompose.ui.common.Loading
import com.example.rickandmortycompose.ui.common.ShowSnackBar
import com.example.rickandmortycompose.ui.season_screen.episode_screen.EpisodeScreen

@Composable
fun SeasonScreen(
    modifier: Modifier = Modifier, navController: NavController = rememberNavController()
) {

    val viewModel: SeasonScreenViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val loadingState = viewModel.dataClass.loadingState.collectAsStateWithLifecycle()
    val errorState = viewModel.dataClass.errorState.collectAsStateWithLifecycle()
    val seasonListState = viewModel.dataClass.seasonList.collectAsStateWithLifecycle()
    val tabItems = seasonListState.value.map { season ->
        SeasonTabItem("Season ${season.number}", season.episodeCount.toString())
    }

    val pagerState = rememberPagerState { tabItems.size }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) selectedTabIndex = pagerState.currentPage
    }

    LaunchedEffect(key1 = errorState.value) {
        if (errorState.value.isEmpty()) return@LaunchedEffect
        snackBarHostState.showSnackbar(errorState.value)
    }
    LaunchedEffect(Unit) {
        viewModel.getSeasons()
    }
    Loading(isLoading = loadingState.value)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (tabItems.isEmpty()) return@Column
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            edgePadding = 0.dp
        ) {
            tabItems.forEachIndexed { index, seasonTabItem ->
                TabItem(modifier = Modifier
                    .padding(8.dp)
                    .height(40.dp),
                    tabItem = seasonTabItem,
                    isSelected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index })
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                val currentSeason = seasonListState.value[pagerState.currentPage]
                EpisodeScreen(episodeIdPair = currentSeason.episodeRange)
            }
        }
    }
    ShowSnackBar(snackBarHostState = snackBarHostState, text = errorState.value)

}

@Composable
fun TabItem(
    modifier: Modifier = Modifier, tabItem: SeasonTabItem, isSelected: Boolean, onClick: () -> Unit
) {
    tabItem.isSelected = isSelected
    Box(modifier = modifier
        .clickable { onClick() }
        .padding(horizontal = 8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tabItem.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp),
                color = tabItem.textColor
            )
//            Text(
//                text = "${tabItem.episodeCount} test ",
//                modifier = Modifier.padding(top = 4.dp),
//                color = Color.Black
//            )
        }
    }
}

