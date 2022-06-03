package com.example.catfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catfacts.R
import com.example.catfacts.ui.theme.CatFactsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    Box(mainViewModel = model)
                    Loader()
                }
            }
        }
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun Box(mainViewModel: MainViewModel) {
    // https://stackoverflow.com/questions/69230831/jetpack-compose-observe-mutablestateof-in-viewmodel

    val (factText, setFactText) = remember {
        mutableStateOf("")
    }

    val composableScope = rememberCoroutineScope()

    Fact(factText)

    Button(
        onClick = {

            composableScope.launch {
//                                val factRequest = factRepository.getFact()
//                                if (factRequest.isSuccessful) {
//                                    val body = factRequest.body()
//
//                                    val fact = body?.fact ?: ""
//
//                                    setFactText(fact)
//
//                                } else {
//                                    Log.d("Batseba", "falha ao obter resultado")
//                                }
            }


        },
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
            .fillMaxWidth(),

        ) {
        Text(text = "Get Fact")
    }

}

@Composable
fun Fact(name: String) {
    Text(text = "$name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatFactsTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cat_loader))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition,
        progress,
    )
}