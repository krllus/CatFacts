package com.example.catfacts.screen.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
    LaunchedEffect(Unit) {

        journalViewModel.setJournalId(journalId)
    }


    val uiState: JournalDetailsScreenUiState by journalViewModel.uiState.collectAsState()

    when (val state = uiState.state) {
        is JournalDetailsUiState.Success -> {
            state.journal?.let { journal ->

                val imageData = journal.imageFilePath?.let { File(it) }

                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageData)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_background)
                            fallback(R.drawable.ic_baseline_image_24)
                            error(R.drawable.ic_baseline_image_24)
                        }).build()
                )

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewJournalDetails() {

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = null)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                fallback(R.drawable.ic_baseline_image_24)
                error(R.drawable.ic_baseline_image_24)
            }).build()
    )
    JournalDetails(
        title = "Title",
        description = stringResource(id = R.string.lorem_long),
        imagePainter = painter
    )
}

@Composable
fun JournalDetails(
    title: String,
    description: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .fillMaxHeight()
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .height(196.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(4.dp)
                ),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}