package com.example.catfacts.screen.journal

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.catfacts.R
import com.example.catfacts.ui.HomeActions
import com.example.catfacts.ui.animations.TakePicture
import com.example.catfacts.ui.common.ErrorScreen
import com.example.catfacts.ui.common.LoadingScreen
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewJournalCrudScreen(

) {
    val navHost = rememberNavController()

    val homeActions = HomeActions(navHost)

    JournalCRUDScreen(homeActions)
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
    val maxTitleLength = 25

    var description by rememberSaveable { mutableStateOf("") }
    val maxDescriptionSize = 255

    val picturePath by journalViewModel.pictureFile.observeAsState()

    val bitmap = picturePath?.let {
        BitmapFactory.decodeFile(it.absolutePath).asImageBitmap()
    }

    val ctx = LocalContext.current

    when (val journalUiState = uiCrudState.state) {
        is JournalCRUDState.Editing -> {

            Column(modifier = Modifier.padding(16.dp)) {

                // https://www.goodrequest.com/blog/jetpack-compose-basics-showing-images
                // https://stackoverflow.com/a/68645041/2811504

                if (bitmap == null) {
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
                            .wrapContentWidth(Alignment.Start)
                            .clickable {
                                val file = createImageFile(ctx)

                                journalViewModel.setPictureFile(file)

                                file.also {
                                    val photoURI: Uri = FileProvider.getUriForFile(
                                        ctx,
                                        "${ctx.packageName}.file_provider",
                                        it
                                    )

                                    takePicture.launch(photoURI)
                                }
                            }
                    ) {
                        TakePicture()
                    }


                } else {
                    Image(
                        bitmap = bitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(4.dp),
                    )
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

@Throws(IOException::class)
fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}