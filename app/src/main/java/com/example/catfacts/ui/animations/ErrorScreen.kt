package com.example.catfacts.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R

@Preview(showBackground = true)
@Composable
fun previewErrorScreen(){
    ErrorScreen(
        Exception("Error, No Network Found"),
        {}
    )
}

@Composable
fun ErrorScreen(
    exception: Throwable,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val errorMessage = exception.message ?: "Unknown error"

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // display lottie cat animation
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.cat_error)
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = modifier.padding(16.dp)
        )

        // display retry button
        // when clicked, retryOperation() is called
        ButtonRetry(errorMessage = errorMessage, onClick = onRetryClicked)
    }


}