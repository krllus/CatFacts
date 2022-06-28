package com.example.catfacts.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.catfacts.data.model.Journal
import java.io.File

@Preview(showBackground = true)
@Composable
fun previewJournalItem() {
    val randomJournal = Journal.randomCapture()

    JournalItem(journal = randomJournal, onJournalItemClicked = {})
}

@Composable
fun JournalItem(
    journal: Journal,
    onJournalItemClicked: (Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    val imageData = journal.imageFilePath?.let { File(it) }

    Column(modifier = modifier.clickable { onJournalItemClicked(Unit) }) {

        if (imageData != null) {
            Image(
                painter = rememberAsyncImagePainter(imageData),
                contentDescription = null
            )
        }

        Text(
            text = journal.title,
            fontSize = 24.sp,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (journal.displayDescription())
            Text(
                text = journal.description,
                textAlign = TextAlign.Justify,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
    }
}