package com.example.catfacts.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.catfacts.data.model.Capture

@Composable
fun CapturedItem(
    capture: Capture,
    onCaptureClicked: (Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    Text(text = capture.description, modifier = modifier)


}