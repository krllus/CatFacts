package com.example.catfacts.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R

@Composable
fun LoadingScreen(setIsPlaying: (Boolean) -> Unit) {
    // https://stackoverflow.com/a/64073703/2811504

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.cat_loader)
    )

//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        iterations = LottieConstants.IterateForever,
//    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}