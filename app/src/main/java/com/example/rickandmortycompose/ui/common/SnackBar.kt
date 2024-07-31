package com.example.rickandmortycompose.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SnackBar(modifier: Modifier = Modifier, text: String) {
    if (text.isBlank()) return
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun ShowSnackBar(
    alignValue: Alignment = Alignment.BottomCenter,
    snackBarHostState: SnackbarHostState,
    text: String
) {
    SnackbarHost(hostState = snackBarHostState) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .padding(vertical = 30.dp)
                .graphicsLayer {
                    shadowElevation = 5f
                }
                .background(color = Color.Gray)
                .padding(vertical = 10.dp)
                .align(alignValue),
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center)
        }
    }
}