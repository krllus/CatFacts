package com.example.catfacts.ui.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R

@Composable
fun TakePicture() {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.take_picture)
    )

    LottieAnimation(
        composition = composition,
        iterations = 1,
    )

}