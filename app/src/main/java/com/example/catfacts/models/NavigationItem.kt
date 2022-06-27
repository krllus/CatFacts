package com.example.catfacts.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.catfacts.utils.Destination

data class NavigationItem(
    val label: Int,
    @DrawableRes val icon: Int,
    val route: Destination,
    val contentDescription: Int
)