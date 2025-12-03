package com.kenneth_demo.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kenneth_demo.ui.screen.*

/**
 * Navigation host for the weather application.
 * Handles navigation between different screens with argument passing.
 * Supports responsive layouts for foldables and large screens.
 */
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Home screen
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                onNavigateToDetail = { cityName ->
                    navController.navigate(Screen.Detail.createRoute(cityName))
                }
            )
        }
        
        // Search screen
        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { cityName ->
                    navController.navigate(Screen.Detail.createRoute(cityName))
                }
            )
        }
        
        // Favorites screen
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { cityName ->
                    navController.navigate(Screen.Detail.createRoute(cityName))
                }
            )
        }
        
        // Detail screen with city name argument
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("cityName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            DetailScreen(
                cityName = cityName,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}