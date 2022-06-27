package com.example.catfacts.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catfacts.data.model.Journal

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

    Text(
        text = journal.description,
        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}