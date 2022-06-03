package com.example.catfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R
import com.example.catfacts.data.Fact
import com.example.catfacts.ui.theme.CatFactsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatFactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel = model)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatFactsTheme {

    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    // https://stackoverflow.com/questions/69230831/jetpack-compose-observe-mutablestateof-in-viewmodel

    val loading by mainViewModel.loading.observeAsState(initial = false)

    val fact by mainViewModel.fact.observeAsState()

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
            Loader(setIsPlaying)
        } else {

            Fact(fact?.getContentIfNotHandled())

            GetFact {
                mainViewModel.loadFact()
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

@Composable
fun Loader(setIsPlaying: (Boolean) -> Unit) {
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