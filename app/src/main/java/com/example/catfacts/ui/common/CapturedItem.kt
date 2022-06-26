package com.example.catfacts.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.catfacts.models.Capture

@Composable
fun CapturedItem(
    capture: Capture,
    modifier: Modifier = Modifier
) {
    Text(text = capture.text, modifier = modifier)
}