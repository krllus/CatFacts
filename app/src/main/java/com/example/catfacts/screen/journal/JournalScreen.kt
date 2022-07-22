package com.example.catfacts.screen.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catfacts.data.model.Journal
import com.example.catfacts.ui.HomeActions
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.JournalItem
import com.example.catfacts.ui.common.LoadingScreen
import com.example.catfacts.ui.icon.IconAddJournal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel = hiltViewModel(),
    homeActions: HomeActions,
    modifier: Modifier = Modifier
) {

    val uiState: JournalScreenUiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeActions.journalCRUDScreen()
                }
            ) {
                IconAddJournal()
            }
        }, content = {
            when (val journalUiState = uiState.state) {
                is JournalUiState.Loading -> {
                    LoadingScreen {}
                }

                is JournalUiState.Success -> {

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        JournalList(
                            journals = journalUiState.journals,
                            onCaptureClicked = { journal ->
                                homeActions.journalDetailsScreen(journal.journalId)
                            }
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
    )
}


@Composable
fun JournalList(
    journals: List<Journal>,
    onCaptureClicked: (Journal) -> Unit,
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
                Spacer(modifier = Modifier.padding(4.dp))
                JournalItem(
                    journal = it,
                    onJournalItemClicked = {
                        onCaptureClicked(it)
                    },
                    modifier = modifier
                )
            }
        }
    }

}

