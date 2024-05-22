package org.bumandhala.eritmobile.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.bumandhala.eritmobile.ui.screen.DetailPemasukanScreen
import org.bumandhala.eritmobile.ui.screen.DetailTabunganScreen
import org.bumandhala.eritmobile.ui.screen.KEY_ID_CATATAN
import org.bumandhala.eritmobile.ui.screen.Landing1
import org.bumandhala.eritmobile.ui.screen.Landing2
import org.bumandhala.eritmobile.ui.screen.Landing3
import org.bumandhala.eritmobile.ui.screen.LandingScreen
import org.bumandhala.eritmobile.ui.screen.Login
import org.bumandhala.eritmobile.ui.screen.MainScreen
import org.bumandhala.eritmobile.ui.screen.Register
import org.bumandhala.eritmobile.ui.screen.Tabungan

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route){
            DetailPemasukanScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_CATATAN)
            DetailPemasukanScreen(navController,id)
        }
        composable(route = Screen.Tabungan.route){
            Tabungan(navController)
        }
        composable(route = Screen.FormBaruTabungan.route){
            DetailTabunganScreen(navController)
        }
    }
}