package com.example.catfacts.screen.captured

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.catfacts.data.model.Capture
import com.example.catfacts.ui.common.CapturedItem
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen

@Composable
fun CapturedScreen(
    viewModel: CapturesViewModel,
    modifier: Modifier = Modifier
) {

    val uiState: CaptureScreenUiState by viewModel.uiState.collectAsState()

    when (val capturesState = uiState.capturesState) {
        is CaptureUiState.Loading -> {
            LoadingScreen {}
        }

        is CaptureUiState.Success -> {
            CapturedList(
                captures = capturesState.captures,
                onCaptureClicked = {}
            )
        }

        is CaptureUiState.Error -> {
            ErrorScreen(
                exception = capturesState.error,
                retryOperation = { }
            )
        }
    }

    Button(onClick = { viewModel.addRandomCapture() }) {
        Text("Add random capture")
    }

}

@Composable
fun CapturedList(
    captures: List<Capture>,
    onCaptureClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        }

        captures.forEach {
            item {
                CapturedItem(
                    capture = it,
                    onCaptureClicked = {
                        onCaptureClicked()
                    }
                )
            }
        }

        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
            )
        }

    }

}

