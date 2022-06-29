package com.example.catfacts.ui.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.catfacts.R

@Preview(showBackground = true)
@Composable
fun IconAddJournal() {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_add_24),
        contentDescription = stringResource(id = R.string.add_journal_content_description)
    )
}