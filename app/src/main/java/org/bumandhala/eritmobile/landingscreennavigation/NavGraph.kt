package org.bumandhala.eritmobile.landingscreennavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.ui.screen.Landing1
import org.bumandhala.eritmobile.ui.screen.Landing2
import org.bumandhala.eritmobile.ui.screen.Landing3
import org.bumandhala.eritmobile.ui.screen.LandingScreen
import org.bumandhala.eritmobile.ui.screen.Register

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
            Landing1(navController)
        }
        composable(route = Screen.Landing2.route) {
            Landing2(navController)
        }
        composable(route = Screen.Landing3.route) {
            Landing3(navController)
        }
        composable(route = Screen.Register.route) {
            Register(navController)
        }
    }
}