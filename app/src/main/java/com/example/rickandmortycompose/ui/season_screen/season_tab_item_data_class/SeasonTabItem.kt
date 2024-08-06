package com.example.rickandmortycompose.ui.season_screen.season_tab_item_data_class

import androidx.compose.ui.graphics.Color
import com.example.rickandmortycompose.ui.theme.Golden

data class SeasonTabItem(val title: String, val episodeCount: String) {
    var isSelected = false
    val textColor
        get() = if (isSelected) selectedColor else unselectedColor

    private val selectedColor = Color.Black
    private val unselectedColor = Golden
}
