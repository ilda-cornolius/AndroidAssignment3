package com.kenneth_demo.ui.navigation

/**
 * Sealed class defining all navigation routes/screens in the app.
 * Used for type-safe navigation in Jetpack Compose Navigation.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String) = "detail/$cityName"
    }
}

