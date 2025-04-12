package com.example.popcornhub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.presentation.home.HomeScreen
//import com.example.popcornhub.home.HomeScreen
import com.example.popcornhub.presentation.details.components.DetailScreen

@Composable
fun WatchAppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { contentId ->
                    navController.navigate(Screen.Details.createRoute(contentId))
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("contentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId") ?: ""
            DetailScreen(
                contenId = contentId, // Match your parameter name from DetailScreen
                contentType = "movie",
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
