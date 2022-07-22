package com.example.catfacts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catfacts.R
import com.example.catfacts.screen.fact.FactScreen
import com.example.catfacts.screen.journal.JournalCRUDScreen
import com.example.catfacts.screen.journal.JournalDetailsScreen
import com.example.catfacts.screen.journal.JournalScreen
import com.example.catfacts.ui.theme.CatFactsTheme
import com.example.catfacts.utils.Constants
import com.example.catfacts.utils.Destination
import dagger.hilt.android.AndroidEntryPoint
import okio.IOException
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    companion object {
        const val KEY_JOURNAL_ID = "journalId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CatFactsTheme {

                val navController = rememberNavController()

                Surface {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = stringResource(id = R.string.app_name))
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(
                                                Destination.JournalList.destinationName
                                            )
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_cat),
                                            contentDescription = stringResource(id = R.string.take_picture_content_description),
                                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            )
                        },
                        content = { padding ->
                            NavHostContainer(
                                navController = navController,
                                padding = padding
                            )
                        }
                    )
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val ctx = this
        val pm = ctx.packageManager

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(pm)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: java.io.IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        ctx,
                        "${ctx.packageName}.file_provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    // takePicture.launch(photoURI)
                }
                Log.d("HomeActivity", "photoFile: ${photoFile?.path}")
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // viewModel.setPictureFile(this)
        }
    }

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation {

        //observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        //observe current route to change the icon color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route


        //Bottom nav items we declared
        Constants.BottomNavigationItens.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route.destinationName,
                onClick = {
                    navController.navigate(navItem.route.destinationName)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(id = navItem.contentDescription),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navItem.label)
                    )
                },
                alwaysShowLabel = false

            )
        }


    }
}

/**
 * It receives navcontroller to navigate between screens,
 * padding values -> Since BottomNavigationavItem.contentDescription n has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    val homeActions = remember(navController) { HomeActions(navController) }

    NavHost(
        navController = navController,

        //set the start destination as home
        startDestination = Destination.JournalList.destinationName,

        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            composable(Destination.Facts.destinationName) {
                FactScreen()
            }

            composable(Destination.JournalList.destinationName) {
                JournalScreen(homeActions)
            }

            composable(Destination.JournalCrud.destinationName) {
                JournalCRUDScreen(homeActions)
            }

            composable(
                route = Destination.JournalDetails.destinationName,
                arguments = listOf(
                    navArgument(HomeActivity.KEY_JOURNAL_ID) { type = NavType.LongType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                JournalDetailsScreen(arguments.getLong(HomeActivity.KEY_JOURNAL_ID), homeActions)
            }
        })

}

class HomeActions(navController: NavHostController) {
    val journalListScreen: () -> Unit = {
        navController.navigate(Destination.JournalList.destinationName)
    }

    val journalCRUDScreen: () -> Unit = {
        navController.navigate(Destination.JournalCrud.destinationName)
    }

    val journalDetailsScreen: (Long) -> Unit = { journalId ->

        val destination = Destination.JournalDetails.destinationName

        val destinationCombined = destination.replace(
            oldValue = "{${HomeActivity.KEY_JOURNAL_ID}}",
            newValue = journalId.toString()
        )

        navController.navigate(destinationCombined)
    }

    val factScreen: () -> Unit = {
        navController.navigate(Destination.Facts.destinationName)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }

}

