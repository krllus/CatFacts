package com.example.catfacts.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R

@Composable
fun ErrorScreen(
    exception: Throwable,
    retryOperation: (Boolean) -> Unit
) {

    val errorMessage = exception.message ?: "Unknown error"

    // display lottie cat animation
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cat_error)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    // display retry button
    // when clicked, retryOperation() is called
    ButtonRetry(error = errorMessage, retry = retryOperation)

}