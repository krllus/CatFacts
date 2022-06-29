package com.example.catfacts.screen.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catfacts.ui.HomeActions

@Composable
fun JournalDetailsScreen(
    journalId: Long,
    homeActions: HomeActions
) {

    val journalViewModel: JournalDetailsViewModel = hiltViewModel()

    val journal by journalViewModel.journal.observeAsState()

    val imagePainter = ColorPainter(Color.Red)

    // initialize journal on viewModel

    Column {

        journal?.let { journal ->
            JournalDetails(journal.title, journal.description, imagePainter)
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