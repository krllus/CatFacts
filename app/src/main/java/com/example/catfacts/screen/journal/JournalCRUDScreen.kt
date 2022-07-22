package com.example.catfacts.screen.journal

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.catfacts.R
import com.example.catfacts.provider.CatFactsFileProvider
import com.example.catfacts.ui.HomeActions
import com.example.catfacts.ui.animations.TakePicture
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen

@Composable
fun JournalCRUDScreen(
    homeActions: HomeActions
) {

    val viewModel: JournalCRUDViewModel = hiltViewModel()

    val uiCrudState: JournalCRUDUiState by viewModel.uiCRUDState.collectAsState()

    val pictureCaptureStatus by viewModel.pictureCaptureStatus.observeAsState()

    val picturePath by viewModel.pictureFilePath.observeAsState()

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { saved ->
        if (saved) {
            viewModel.changePictureStatusToCaptured()
        } else {
            viewModel.changePictureStatusToError()
        }
    }

    var title by rememberSaveable { mutableStateOf("") }
    val maxTitleLength = 25

    var description by rememberSaveable { mutableStateOf("") }
    val maxDescriptionSize = 255

    val ctx = LocalContext.current

    // https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images
    // https://stackoverflow.com/a/68645041/2811504
    // https://fvilarino.medium.com/using-activity-result-contracts-in-jetpack-compose-14b179fb87de
    // https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090

    when (val journalUiState = uiCrudState.state) {
        is JournalCRUDState.Editing -> {

            Column(modifier = Modifier.padding(16.dp)) {

                if (pictureCaptureStatus != PictureCaptureStatus.Captured || picturePath == null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(196.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                val file = CatFactsFileProvider.getImageFile(ctx)
                                viewModel.changePictureStatusToCapturing()
                                viewModel.setPictureFile(file)
                                val pictureUri = CatFactsFileProvider.getImageUri(ctx, file)
                                takePicture.launch(pictureUri)
                            }
                    ) {
                        TakePicture()
                    }
                }

                if (pictureCaptureStatus == PictureCaptureStatus.Captured && picturePath != null) {
                    picturePath?.let { path ->
                        val picture = CatFactsFileProvider.getFileFromAbsolutePath(path)
                        val pictureUri = CatFactsFileProvider.getImageUri(ctx, picture)

                        AsyncImage(
                            model = pictureUri,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(196.dp)
                                .padding(4.dp),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = title,
                    onValueChange = {
                        title = if (it.length > maxTitleLength) {
                            it.substring(0, maxTitleLength)
                        } else {
                            it
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    label = { Text(stringResource(id = R.string.label_title)) },
                    modifier = Modifier.fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = {
                        description = if (it.length > maxDescriptionSize) {
                            it.substring(0, maxDescriptionSize)
                        } else {
                            it
                        }
                    },
                    singleLine = false,
                    label = { Text(stringResource(id = R.string.label_description)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.saveJournal(
                            title = title,
                            description = description
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.button_save))
                }

            }

        }

        is JournalCRUDState.Saving -> {
            LoadingScreen {}
        }

        is JournalCRUDState.Saved -> {
            homeActions.journalListScreen()
        }

        is JournalCRUDState.Error -> {
            ErrorScreen(
                exception = journalUiState.error,
                onRetryClicked = {
                    viewModel.resetCrudState()
                }
            )
        }
    }
}