package com.example.catfacts.ui.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.catfacts.R

@Preview(showBackground = true)
@Composable
fun IconTakePicture() {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_camera),
        contentDescription = stringResource(id = R.string.take_picture_content_description)
    )
}