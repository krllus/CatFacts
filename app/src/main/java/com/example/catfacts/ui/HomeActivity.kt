package com.example.catfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.catfacts.screen.captured.CapturedScreen
import com.example.catfacts.screen.fact.FactScreen
import com.example.catfacts.ui.theme.CatFactsTheme
import com.example.catfacts.utils.Constants
import com.example.catfacts.utils.Destination
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatFactsTheme {
                val navController = rememberNavController()

                Surface(color = Color.White) {

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
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
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(
        //set background color
        backgroundColor = Color(0xFF0F9D58)
    ) {

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
                        imageVector = navItem.icon,

                        contentDescription = stringResource(id = navItem.contentDescription),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navItem.label)
                    )
                }, alwaysShowLabel = false

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

    NavHost(
        navController = navController,

        //set the start destination as home
        startDestination = Destination.Facts.destinationName,

        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            composable(Destination.Facts.destinationName) {
                FactScreen(hiltViewModel())
            }

            composable(Destination.Captured.destinationName) {
                CapturedScreen(hiltViewModel())
            }
        })

}