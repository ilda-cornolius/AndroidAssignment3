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


 //This class sets up navigation methods between screens in the application
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    NavHost(
        //creating the navigation controller and setting the home screen as the startDestination
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // Creates navigationController functionality in the home screen
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
        
        // Defines the search screen
        composable(Screen.Search.route) {
            //When the user presses the back button it goes to the previous screen
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                //when the user presses the card it goes to the detail screen
                onNavigateToDetail = { cityName ->
                    navController.navigate(Screen.Detail.createRoute(cityName))
                }
            )
        }
        
        // Defines the favorites screen
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
        
        // Defines the detail screen with city name argument
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