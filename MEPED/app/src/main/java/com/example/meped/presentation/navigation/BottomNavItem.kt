package com.example.meped.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Schedule : BottomNavItem("schedule", "Schedule", Icons.Default.DateRange)
    object Explore : BottomNavItem("explore", "Explore", Icons.Default.Search)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.AccountCircle)
}