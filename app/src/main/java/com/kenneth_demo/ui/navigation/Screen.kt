package com.kenneth_demo.ui.navigation


 //This class defines all navigation routes
 //This makes it easier to access the home, search, favorites, and detail screens
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String) = "detail/$cityName"
    }
}

