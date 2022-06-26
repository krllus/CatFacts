package com.example.catfacts.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.catfacts.utils.Destination

data class NavigationItem(
    val label: Int,
    val icon: ImageVector,
    val route: Destination,
    val contentDescription: Int
)