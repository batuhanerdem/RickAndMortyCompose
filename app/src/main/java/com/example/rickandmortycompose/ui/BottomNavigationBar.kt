package com.example.rickandmortycompose.ui

import Screens
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.rickandmortycompose.domain.model.navigation.BottomNavItem
import com.example.rickandmortycompose.ui.character_screen.CharacterScreen
import com.example.rickandmortycompose.ui.character_screen.character_details.CharacterDetailsScreen
import com.example.rickandmortycompose.ui.location_screen.LocationScreen
import com.example.rickandmortycompose.ui.location_screen.characters_in_location_screen.CharactersInLocationScreen
import com.example.rickandmortycompose.ui.season_screen.SeasonScreen
import com.example.rickandmortycompose.ui.theme.Background
import com.example.rickandmortycompose.ui.theme.PortalGreen
import com.example.rickandmortycompose.ui.theme.RickAndMortyComposeTheme

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomAppBar(
        containerColor = Background, modifier = modifier.border(0.5.dp, Color.Black)
    ) {
        val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

        val currentDest = navBackStackEntry?.destination
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(modifier = Modifier.fillMaxSize(),
                selected = currentDest?.hasRoute(item.screenType::class) ?: false,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenType) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Background,
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = PortalGreen,
                    unselectedTextColor = PortalGreen
                ),
                icon = { Icon(item.icon, contentDescription = null, modifier.fillMaxHeight(0.4f)) },
                label = {
                    Text(
                        item.label,
                        modifier
                            .padding(top = 10.dp)
                            .fillMaxHeight(0.2f)
                            .align(Alignment.Bottom)
                    )
                }) // buraya bak
        }
    }
}

@Composable
fun NavigationHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navHostController, startDestination = Screens.Character, modifier = modifier
    ) {
        val defaultModifier = Modifier.padding(start = 12.5.dp, end = 12.5.dp, top = 20.dp)

        composable<Screens.Location> {
            LocationScreen(
                navController = navHostController,
            )
        }
        composable<Screens.Character> {
            CharacterScreen(
                navController = navHostController,
            )
        }
        composable<Screens.Season> { SeasonScreen() }
        composable<Screens.Episode> { Text(text = "episode") }
        composable<Screens.CharacterDetails> {
            val args = it.toRoute<Screens.CharacterDetails>()
            CharacterDetailsScreen(characterId = args.characterId)
        }
        composable<Screens.CharactersInLocation> {
            val args = it.toRoute<Screens.CharactersInLocation>()
            CharactersInLocationScreen(
                idList = args.characterIdList,
                navController = navHostController,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomAppBarPreview() {
    RickAndMortyComposeTheme {
        val navHostController = rememberNavController()
        BottomNavigationBar(navController = navHostController)
    }
}