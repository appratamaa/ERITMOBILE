package org.bumandhala.eritmobile.landingscreennavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.ui.screen.Landing1
import org.bumandhala.eritmobile.ui.screen.Landing2
import org.bumandhala.eritmobile.ui.screen.Landing3
import org.bumandhala.eritmobile.ui.screen.LandingScreen
import org.bumandhala.eritmobile.ui.screen.Login
import org.bumandhala.eritmobile.ui.screen.MainScreen
import org.bumandhala.eritmobile.ui.screen.PopupRegister
import org.bumandhala.eritmobile.ui.screen.Register

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.LandingScreen.route
    ) {
        composable(route = Screen.LandingScreen.route) {
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
        composable(route = Screen.Login.route) {
            Login(navController)
        }
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
    }
}