package com.example.catfacts.screen.journal

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catfacts.R
import com.example.catfacts.ui.HomeActions
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewJournalCrudScreen(
    @PreviewParameter(HomeActionsPreviewParameter::class) homeActions: HomeActions
) {
    JournalCRUDScreen(homeActions)
}

class HomeActionsPreviewParameter() : PreviewParameterProvider<HomeActions> {
    override val values: Sequence<HomeActions> = sequenceOf(

    )
}

@Composable
fun JournalCRUDScreen(
    homeActions: HomeActions
) {

    val journalViewModel: JournalCRUDViewModel = hiltViewModel()

    val uiCrudState: JournalCRUDUiState by journalViewModel.uiCRUDState.collectAsState()

    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { saved ->
        if (saved) {
            journalViewModel.changePictureStatusToCaptured()
        } else {
            journalViewModel.changePictureStatusToError()
        }
    }

    var title by rememberSaveable { mutableStateOf("") }

    var description by rememberSaveable { mutableStateOf("") }

    val picturePath by journalViewModel.pictureFile.observeAsState()



    when (val journalUiState = uiCrudState.state) {
        is JournalCRUDState.Editing -> {

            Column {

                //  https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images
                // https://stackoverflow.com/a/68645041/2811504

                picturePath?.let {

                    val bitmap = BitmapFactory.decodeFile(it.absolutePath).asImageBitmap()

                    Image(
                        bitmap = bitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(4.dp),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = { Text(stringResource(id = R.string.label_title)) },
                    modifier = Modifier.fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text(stringResource(id = R.string.label_description)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        journalViewModel.saveJournal(
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
                    journalViewModel.resetCrudState()
                }
            )
        }
    }
}
