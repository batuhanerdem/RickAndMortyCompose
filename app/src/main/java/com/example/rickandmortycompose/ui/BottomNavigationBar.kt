package com.example.rickandmortycompose.ui

import Screens
import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.rickandmortycompose.domain.model.navigation.BottomNavItem
import com.example.rickandmortycompose.ui.character.CharacterScreen
import com.example.rickandmortycompose.ui.character.character_details.CharacterDetailsScreen

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    BottomAppBar(
        containerColor = Color.Yellow,
        contentColor = Color.Black,
        modifier = modifier.background(Color.Yellow)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
//        val currentRoute = navBackStackEntry?.toRoute<Screens>()

        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(modifier = Modifier,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.screenType) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) })
        }
    }
}

@Composable
fun NavigationHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navHostController, startDestination = Screens.Character, modifier = modifier
    ) {
        composable<Screens.Location> { Text(text = "Location") }
        composable<Screens.Character> { CharacterScreen(navController = navHostController) }
        composable<Screens.Episode> { Text(text = "episode") }
        composable<Screens.CharacterDetails> {
            val args = it.toRoute<Screens.CharacterDetails>()
//            val characterId: String = it.toRoute()
            CharacterDetailsScreen(characterId = args.characterId)
        }
    }
}