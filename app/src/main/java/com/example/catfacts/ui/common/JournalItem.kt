package com.example.catfacts.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.catfacts.data.model.Journal
import java.io.File

import com.example.catfacts.R

@Preview(showBackground = true)
@Composable
fun previewJournalItem() {
    val randomJournal = Journal.randomCapture()

    JournalItem(journal = randomJournal, onJournalItemClicked = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalItem(
    journal: Journal,
    onJournalItemClicked: (Journal) -> Unit,
    modifier: Modifier = Modifier
) {

    val imageData = journal.imageFilePath?.let { File(it) }

    val painter = rememberImagePainter(data = imageData, builder = {
        crossfade(true)
        placeholder(R.drawable.ic_launcher_background)
        fallback(R.drawable.ic_baseline_image_24)
        error(R.drawable.ic_baseline_image_24)
    })

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onJournalItemClicked(journal) }
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painter,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentDescription = null
            )

            Column {
                Text(
                    text = journal.title,
                    fontSize = 24.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                if (journal.displayDescription())
                    Text(
                        text = journal.description,
                        textAlign = TextAlign.Justify,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )

            }


        }


    }
}