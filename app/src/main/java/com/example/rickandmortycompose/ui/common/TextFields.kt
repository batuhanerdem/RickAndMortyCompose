package com.example.rickandmortycompose.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.rickandmortycompose.ui.theme.PortalGreen


@Composable
fun TextFields(
    modifier: Modifier = Modifier, text: String, textColor: Color = PortalGreen, maxLines: Int = 2
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Start,
            minLines = 1,
            fontSize = TextUnit(15f, TextUnitType.Sp),
            color = textColor,
            maxLines = 2,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
        )
    }
}