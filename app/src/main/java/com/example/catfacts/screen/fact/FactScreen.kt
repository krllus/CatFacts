package com.example.catfacts.screen.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R
import com.example.catfacts.data.model.Fact
import com.example.catfacts.ui.common.LoadingScreen


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CatFactsTheme {
//        FactScreen(FactViewModel())
//    }
//}

@Composable
fun FactScreen(factsViewModel: FactsViewModel) {

    // https://stackoverflow.com/questions/69230831/jetpack-compose-observe-mutablestateof-in-viewmodel

    val loading by factsViewModel.loading.observeAsState(initial = false)

    val fact by factsViewModel.fact.observeAsState()

    val (isPlaying, setIsPlaying) = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // if (loading && isPlaying) {

        if (loading) {
            LoadingScreen(setIsPlaying)
        } else {

            Fact(fact?.getContentIfNotHandled())

            GetFact {
                factsViewModel.loadFact()
            }
        }
    }
}

@Composable
fun Fact(fact: Fact?) {

    val text = fact?.fact ?: "Click in the button to get a new fact"

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun GetFact(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        BasicText(text = "Get Fact")
    }
}

