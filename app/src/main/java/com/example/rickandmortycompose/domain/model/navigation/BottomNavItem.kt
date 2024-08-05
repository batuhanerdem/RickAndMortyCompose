package com.example.rickandmortycompose.domain.model.navigation

import Screens
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val screenType: Screens, val icon: ImageVector, val label: String
) {
    Location(Screens.Location, Icons.Default.LocationOn, "Location"),
    Character(Screens.Character, Icons.Default.Person, "Character"),
    Season(Screens.Season, Icons.Default.List, "Season")

}