package com.example.catfacts.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catfacts.R


@Preview(showBackground = true)
@Composable
fun previewButtonRetry() {
    ButtonRetry(
        "error, nao foi possivel fazer operacao ABC",
        {}
    )
}

@Composable
fun ButtonRetry(
    errorMessage: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val padding = 16.dp

    Column(
        modifier = modifier.padding(padding)
    ) {
        Text(text = errorMessage)
        Spacer(modifier = Modifier.size(4.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClick,
        ) {
            Text(text = stringResource(R.string.button_retry))
        }
    }


}