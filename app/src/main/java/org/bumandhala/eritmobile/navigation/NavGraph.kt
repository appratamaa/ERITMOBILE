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
import org.bumandhala.eritmobile.ui.screen.DetailPengeluaranScreen
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PEMASUKAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PENGELUARAN
import org.bumandhala.eritmobile.ui.screen.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route){
            DetailPemasukanScreen(navController)
        }
        composable(
            route = Screen.FormUbahPemasukan.route,
            arguments = listOf(
                navArgument(KEY_ID_PEMASUKAN) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val idPemasukan = navBackStackEntry.arguments?.getLong(KEY_ID_PEMASUKAN)
            DetailPemasukanScreen(navController,idPemasukan)
        }
        composable(
            route = Screen.FormUbahPengeluaran.route,
            arguments = listOf(
                navArgument(KEY_ID_PENGELUARAN) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val idPengeluaran = navBackStackEntry.arguments?.getLong(KEY_ID_PENGELUARAN)
            DetailPengeluaranScreen(navController,idPengeluaran)
        }
    }
}