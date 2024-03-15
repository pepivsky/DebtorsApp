package com.pepivsky.debtorsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pepivsky.debtorsapp.data.models.SharedViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pepivsky.debtorsapp.DetailDebtorScreen
import com.pepivsky.debtorsapp.HomeScreen


@Composable
fun AppNavigation(viewModel: SharedViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(viewModel = viewModel, navController)
        }

        composable(
            route = AppScreens.MovementsScreen.route,
            arguments = listOf(navArgument(name = AppScreens.MovementsScreen.param) {
                type = NavType.LongType
            })
        ) {
            DetailDebtorScreen(
                viewModel = viewModel,
                navController = navController,
                id = it.arguments?.getLong(AppScreens.MovementsScreen.param) ?: 0
            )
        }

    }
}