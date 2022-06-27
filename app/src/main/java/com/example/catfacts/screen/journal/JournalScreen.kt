package com.example.catfacts.screen.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catfacts.data.model.Journal
import com.example.catfacts.ui.common.JournalItem
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen

@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    modifier: Modifier = Modifier
) {

    val uiState: JournalScreenUiState by viewModel.uiState.collectAsState()

    when (val journalUiState = uiState.journalsState) {
        is JournalUiState.Loading -> {
            LoadingScreen {}
        }

        is JournalUiState.Success -> {

            Column(modifier = modifier.padding(16.dp)) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.addRandomCapture() }) {
                    Text("Add random capture")
                }

                Spacer(modifier = Modifier.height(16.dp))

                JournalList(
                    journals = journalUiState.journals,
                    onCaptureClicked = {}
                )
            }

        }

        is JournalUiState.Error -> {
            ErrorScreen(
                exception = journalUiState.error,
                onRetryClicked = { }
            )
        }
    }
}

@Composable
fun JournalList(
    journals: List<Journal>,
    onCaptureClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        }

        journals.forEach {
            item {
                JournalItem(
                    journal = it,
                    onJournalItemClicked = {
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

