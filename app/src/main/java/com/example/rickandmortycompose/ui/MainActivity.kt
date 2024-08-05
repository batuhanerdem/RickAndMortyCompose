package com.example.rickandmortycompose.ui

import Screens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortycompose.R
import com.example.rickandmortycompose.ui.theme.Background
import com.example.rickandmortycompose.ui.theme.RickAndMortyComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(getColor(R.color.black)))
        setContent {
            RickAndMortyComposeTheme {
                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDest = navBackStackEntry?.destination
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    if (currentDest?.hasRoute(Screens.CharacterDetails::class) == false && !currentDest.hasRoute(
                            Screens.CharactersInLocation::class
                        )
                    ) BottomNavigationBar(
                        navController = navHostController
                    )
                }) { innerPadding ->
                    NavigationHost(
                        navHostController = navHostController,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(Background)
                    )
                }
            }
        }
    }
}
