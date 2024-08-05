package com.example.rickandmortycompose.ui.common.character

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmortycompose.domain.model.Character


@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    onCharacterClicked: (Character) -> Unit = {},
    characters: List<Character>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier.fillMaxSize(),
//            .background(Color.Green)
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.Top,
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
        .padding(vertical = 5.dp, horizontal = 5.dp)
        .fillMaxWidth()
        .clickable { onCharacterClicked(character) }
        .fillMaxHeight(0.4f)) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
//                colorFilter = ColorFilter.tint(Color.Cyan.copy(alpha = 0.5f)),
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .aspectRatio(1f)
            )
            Text(
                text = character.name,
                textAlign = TextAlign.Center,
                minLines = 1,
                color = Color.White,
                maxLines = 2,
                fontWeight = FontWeight.SemiBold,
                lineHeight = TextUnit(20f, TextUnitType.Sp),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Black.copy(alpha = 0.05f))

            )
        }
    }
}