package com.pepivsky.debtorsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pepivsky.debtorsapp.ui.screens.SettingsScreen
import com.pepivsky.debtorsapp.ui.screens.detailDebtor.DetailDebtorScreen
import com.pepivsky.debtorsapp.ui.screens.home.HomeScreen


@Composable
fun AppNavigation(viewModel: SharedViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreenNav) {
        composable<HomeScreenNav> {
            HomeScreen(viewModel = viewModel, navController)
        }

        composable<DetailDebtorScreenNav> { navBackStackEntry ->
            val id = navBackStackEntry.toRoute<DetailDebtorScreenNav>().debtorId

            LaunchedEffect(key1 = id, block = {
                viewModel.getSelectedDebtorWithMovementsById(id = id)
            })

            val selectedDebtorWithMovements by viewModel.selectedDebtorWithMovements.collectAsState()

            selectedDebtorWithMovements?.let {
                DetailDebtorScreen(
                    viewModel = viewModel,
                    navController = navController,
                    it
                )
            }
        }

        composable<SettingsScreenNav> {
            SettingsScreen(navController = navController)
        }

    }
}