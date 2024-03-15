package com.pepivsky.debtorsapp.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen: AppScreens("home_screen")

    object MovementsScreen: AppScreens("movements_screen" + "/{id}") {
        const val param = "id"
        fun createRoute(id: Long) = "movements_screen/$id"
    }
}