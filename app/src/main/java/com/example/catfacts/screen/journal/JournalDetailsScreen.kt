package com.example.catfacts.screen.journal

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.catfacts.R
import com.example.catfacts.ui.HomeActions
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen
import java.io.File

@Composable
fun JournalDetailsScreen(
    journalId: Long,
    homeActions: HomeActions
) {

    val journalViewModel: JournalDetailsViewModel = hiltViewModel()

    // https://stackoverflow.com/a/69043272/2811504
    LaunchedEffect(Unit){
        Log.d("JournalDetailsScreen", "LaunchedEffect")
        journalViewModel.setJournalId(journalId)
    }

    val uiState: JournalDetailsScreenUiState by journalViewModel.uiState.collectAsState()

    when (val state = uiState.state) {
        is JournalDetailsUiState.Success -> {
            state.journal?.let { journal ->

                val imageData = journal.imageFilePath?.let { File(it) }

                val painter = rememberImagePainter(data = imageData, builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    fallback(R.drawable.ic_baseline_image_24)
                    error(R.drawable.ic_baseline_image_24)
                })

                JournalDetails(
                    title = journal.title,
                    description = journal.description,
                    imagePainter = painter
                )
            }


        }

        is JournalDetailsUiState.Loading -> {
            LoadingScreen {}
        }

        is JournalDetailsUiState.Error -> {
            ErrorScreen(
                exception = state.error,
                onRetryClicked = {
                    homeActions.navigateUp()
                }
            )
        }

    }
}

@Composable
fun JournalDetails(
    title: String,
    description: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(painter = imagePainter, contentDescription = null)
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}