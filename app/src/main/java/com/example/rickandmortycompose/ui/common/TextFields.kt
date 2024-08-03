package com.example.rickandmortycompose.ui.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp


@Composable
fun TextFields(
    modifier: Modifier = Modifier,
    staticText: String,
    dynamicText: String,
    staticTextColor: Color = Color.Yellow,
    dynamicTextColor: Color = Color.White,
    maxLines: Int = 2
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Log.d("tag", "TextFields:$staticText ")
        Text(
            text = "$staticText:",
            textAlign = TextAlign.Start,
            minLines = 1,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = staticTextColor,
            maxLines = 2,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
//                .fillMaxWidth()
                .padding(start = 10.dp)
        )
        Text(
            text = dynamicText,
            textAlign = TextAlign.Start,
            minLines = 1,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            color = dynamicTextColor,
            maxLines = maxLines,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
//                .fillMaxWidth()
                .padding(start = 10.dp)
        )
    }
}