package org.bumandhala.eritmobile.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.bumandhala.eritmobile.model.User
import org.bumandhala.eritmobile.navigation.Screen.Companion.KEY_USER_ID
import org.bumandhala.eritmobile.ui.screen.DetailPemasukanScreen
import org.bumandhala.eritmobile.ui.screen.DetailPengeluaranScreen
import org.bumandhala.eritmobile.ui.screen.DetailTabunganScreen
import org.bumandhala.eritmobile.ui.screen.DetailTabunganTabungan
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PEMASUKAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PENGELUARAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_TABUNGAN
import org.bumandhala.eritmobile.ui.screen.KEY_NAMA_TABUNGAN
import org.bumandhala.eritmobile.ui.screen.Landing1
import org.bumandhala.eritmobile.ui.screen.Landing2
import org.bumandhala.eritmobile.ui.screen.Landing3
import org.bumandhala.eritmobile.ui.screen.LandingScreen
import org.bumandhala.eritmobile.ui.screen.Login
import org.bumandhala.eritmobile.ui.screen.MainScreen
import org.bumandhala.eritmobile.ui.screen.ProfileScreen
import org.bumandhala.eritmobile.ui.screen.Register
import org.bumandhala.eritmobile.ui.screen.ResetPassword
import org.bumandhala.eritmobile.ui.screen.Tabungan
import org.bumandhala.eritmobile.ui.screen.TambahTabungan
import org.bumandhala.eritmobile.util.UserDataStore


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    var startDestination = "";
    startDestination = if (!user.signedIn) {
        Screen.LandingScreen.route
    } else {
        Screen.Home.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaruPemasukan.route) {
            DetailPemasukanScreen(navController)
        }
        composable(route = Screen.FormBaruPengeluaran.route) {
            DetailPengeluaranScreen(navController)
        }
        composable(
            route = Screen.FormUbahPemasukan.route,
            arguments = listOf(
                navArgument(KEY_ID_PEMASUKAN) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val idPemasukan = navBackStackEntry.arguments?.getLong(KEY_ID_PEMASUKAN)
            DetailPemasukanScreen(navController, idPemasukan)
        }
        composable(
            route = Screen.FormUbahPengeluaran.route,
            arguments = listOf(
                navArgument(KEY_ID_PENGELUARAN) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val idPengeluaran = navBackStackEntry.arguments?.getLong(KEY_ID_PENGELUARAN)
            DetailPengeluaranScreen(navController, idPengeluaran)
        }


        composable(route = Screen.LandingScreen.route) {
            LandingScreen(navController)
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
        composable(route = Screen.FormBaru.route) {
            DetailPemasukanScreen(navController)
        }
        composable(route = Screen.Tabungan.route) {
            Tabungan(navController)
        }
        composable(route = Screen.FormBaruTabungan.route) {
            DetailTabunganScreen(navController)
        }
        composable(
            route = Screen.DetailTabunganTabungan.route,
            arguments = listOf(
                navArgument(KEY_ID_TABUNGAN) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_TABUNGAN)
            DetailTabunganTabungan(navController, id)
        }
        composable(route = Screen.TambahtabunganBaru.route) {
            TambahTabungan(navController)
        }
        composable(
            route = Screen.Tambahtabungan.route,
            arguments = listOf(
                navArgument(KEY_NAMA_TABUNGAN) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val namatabungan = navBackStackEntry.arguments?.getString(KEY_NAMA_TABUNGAN)
            TambahTabungan(navController, namatabungan)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
//        composable(
//            route = "profile/{email}",
//            arguments = listOf(navArgument("email") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("email") ?: ""
//            ProfileScreen(navController = navController, email = email)
//        }
        composable(route = Screen.ResetPassword.route){
            ResetPassword(navController)
        }
        }
    }
