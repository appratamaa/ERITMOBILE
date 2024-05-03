package org.bumandhala.eritmobile.landingscreennavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.ui.screen.Landing1
import org.bumandhala.eritmobile.ui.screen.LandingScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            LandingScreen (navController)
        }
        composable(route = Screen.Landing1.route) {
            Landing1()
        }
    }
}