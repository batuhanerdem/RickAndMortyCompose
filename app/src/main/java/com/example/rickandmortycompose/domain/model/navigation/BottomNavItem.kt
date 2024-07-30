package com.example.rickandmortycompose.domain.model.navigation

import Screens
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val route: String, val screenType: Screens, val icon: ImageVector, val label: String
) {
    Location("location", Screens.Location, Icons.Default.LocationOn, "Location"), Character(
        "character", Screens.Character, Icons.Default.Person, "Character"
    ),
    Episode("episode", Screens.Episode, Icons.Default.List, "Episode")

}