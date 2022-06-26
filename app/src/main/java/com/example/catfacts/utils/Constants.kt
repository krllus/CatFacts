package com.example.catfacts.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import com.example.catfacts.R
import com.example.catfacts.models.NavigationItem

object Constants {
    val BottomNavigationItens = listOf(
        NavigationItem(
            label = R.string.navigation_item_captured,
            icon = Icons.Filled.Add,
            route = Destination.Captured,
            contentDescription = R.string.navigation_item_captured_description
        ),
        NavigationItem(
            label = R.string.navigation_item_facts,
            icon = Icons.Filled.Home,
            route = Destination.Facts,
            contentDescription = R.string.navigation_item_facts_description
        )
    )
}

sealed class Destination(val destinationName: String) {
    object Captured : Destination("captured")
    object Facts : Destination("facts")
}