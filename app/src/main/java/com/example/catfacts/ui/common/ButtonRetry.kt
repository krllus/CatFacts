package com.example.catfacts.ui.common

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.catfacts.R

@Composable
fun ButtonRetry(
    error: String,
    retry: (Boolean) -> Unit
) {
    Text(text = error)
    Button(
        onClick = {
            retry(true)
        }
    ) {
        Text(text = stringResource(R.string.button_retry))
    }
}