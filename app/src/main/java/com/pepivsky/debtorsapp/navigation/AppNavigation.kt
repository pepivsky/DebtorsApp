package com.pepivsky.debtorsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pepivsky.debtorsapp.ui.screens.AboutScreen
import com.pepivsky.debtorsapp.ui.screens.detailDebtor.DetailDebtorScreen
import com.pepivsky.debtorsapp.ui.screens.home.HomeScreen


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
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(AppScreens.MovementsScreen.param)

            LaunchedEffect(key1 = id, block = {
                if (id != null) {
                    viewModel.getSelectedDebtorWithMovementsById(id = id)
                }
            })

            val selectedDebtorWithMovements by viewModel.selectedDebtorWithMovements.collectAsState()

            if (id != null) {
                selectedDebtorWithMovements?.let {
                    DetailDebtorScreen(
                        viewModel = viewModel,
                        navController = navController,
                        it
                    )
                }
            }
        }

        composable(
            route = AppScreens.AboutScreen.route
        ) {
            AboutScreen(navController = navController)
        }

    }
}