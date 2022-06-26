package com.example.catfacts.screen.captured

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.catfacts.ui.common.CapturedItem

@Composable
fun CapturedScreen(
    viewModel: CapturesViewModel,
    modifier: Modifier = Modifier
) {

    val captures = viewModel.captures

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        }

        captures.forEach {
            item {
                CapturedItem(it)
            }
        }

        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
            )
        }

    }

}

