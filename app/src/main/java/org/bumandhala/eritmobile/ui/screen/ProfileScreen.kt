package org.bumandhala.eritmobile.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.EritDb
import org.bumandhala.eritmobile.model.ProfileViewModel
import org.bumandhala.eritmobile.model.User
import org.bumandhala.eritmobile.navigation.Screen
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ProfileViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))
    val context = LocalContext.current
    val db = EritDb.getInstance(context)
    val factory = ProfileViewModelFactory(db.dao)
    val viewModel: ProfileViewModel = viewModel(factory = factory)

    // Mengambil state dari Flow
    val userState by viewModel.user.collectAsState()
    

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = poppinsblack,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF20BCCB),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color.White)
            ) {
                IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = "Beranda",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF20BCCB)
                    )
                }

                IconButton(onClick = {
                    Toast.makeText(context, "Fitur grafik masih dalam tahap pengembangan", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.graph),
                        contentDescription = "Grafik",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF20BCCB)
                    )
                }

                IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profil",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF20BCCB)
                    )
                }
            }
        }
    ) {  paddingValues ->
        Surface(
            color = Color.White,
            modifier = Modifier.padding(paddingValues)
        ) {
            userState?.let { user ->
                ProfileContent(user = user, navController = navController)
            }
        }
    }
}

@Composable
fun ProfileContent(user: User, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

        // Tambahkan gambar profil di sini
        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Gambar Profil",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileItem(label = "Nama", text = user.name)
            ProfileItem(label = "Nama Pengguna", text = user.userName)
            ProfileItem(label = "Email", text = user.email)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* Navigasi ke layar edit profil */ },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(Color(0xFF263AA2))
            ) {
                Text(text = "Edit Profil", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { navController.navigate(Screen.LandingScreen.route) }, // Navigasi ke LandingScreen
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(Color(0xFF20BCCB))
            ) {
                Text(text = "Keluar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = text, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ERITMOBILETheme {
//        val user = User(
//            name = "John Doe",
//            userName = "john_doe",
//            email = "john.doe@example.com",
//            password = "password",
//            confirmation = "password",
//            signedIn = false
//        )
//        ProfileContent(user = user, navController = rememberNavController())
        ProfileScreen(navController = rememberNavController()) // Ganti 1 dengan ID pengguna yang valid
    }
}


