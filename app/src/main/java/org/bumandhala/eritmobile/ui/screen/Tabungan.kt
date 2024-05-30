package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.TabunganScreenDb
import org.bumandhala.eritmobile.model.Tabunganscreen
import org.bumandhala.eritmobile.navigation.Screen
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.SettingsDataStore
import org.bumandhala.eritmobile.util.ViewModelFactoryTabunganScreen

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tabungan(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))
    val context = LocalContext.current
    SettingsDataStore(LocalContext.current)

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
                ),
                modifier = Modifier
                    .fillMaxWidth() // Menetapkan lebar penuh untuk TopAppBar
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaruTabungan.route)
                }, containerColor = Color(0xFF20BCCB)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_tabungan),
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color.White)
            ) {
                // Tombol Beranda
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
    ) { padding ->
        Surface(color = Color.White) {
            // Menampilkan konten layar utama
            ContentTabungan(
                modifier = Modifier
                    .padding(padding)
                    .background(color = MaterialTheme.colorScheme.tertiary),
                navController = navController
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentTabungan(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = TabunganScreenDb.getInstance(context)
    val factory = ViewModelFactoryTabunganScreen(db.dao)
    val viewModel: MainViewModelTabunganScreen = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFE5E7EE))
    ) {
        // Menampilkan salam dan waktu tanggal
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Tetapkan padding di sini
            horizontalArrangement = Arrangement.Center,// Menetapkan horizontal alignment ke tengah
            verticalAlignment = Alignment.CenterVertically // Menetapkan vertikal alignment ke tengah
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Spacer untuk memberikan ruang di antara elemen
            CalendarIconAndDateTime()
        }

        // Menampilkan tombol-tombol
        Row {
            ButtonTabungan(navController) // Memanggil fungsi MyButtons yang telah dibuat sebelumnya
        }
        if (data.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(color = Color(0xFFE5E7EE))
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.list_kosong))
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(color = Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItemTabungan(tabunganscreen = it) {
                        navController.navigate(Screen.DetailTabunganTabungan.withId(it.id))
                    }
                }
            }
        }
    }
}
@Composable
fun ButtonTabungan(navController: NavHostController) {
    var isPemasukanClicked by remember { mutableStateOf(false) } // Ubah menjadi false
    var isPengeluaranClicked by remember { mutableStateOf(false) }
    var isTabunganClicked by remember { mutableStateOf(true) } // Ubah menjadi true

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 0.dp,
                bottom = 20.dp,
                start = 16.dp,
                end = 16.dp
            ) // Atur padding di sini
            .background(color = Color.White, shape = RoundedCornerShape(50))
            .height(39.dp), // Bentuk tombol
        horizontalArrangement = Arrangement.SpaceBetween, // Atur penempatan tombol secara horizontal
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                isPemasukanClicked = true
                isPengeluaranClicked = false
                isTabunganClicked = false
                navController.navigate(Screen.Home.route)
            },
            colors = ButtonDefaults.buttonColors(if (isPemasukanClicked) Color(0xFF00B4FF) else Color.White),
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Pemasukan",
                color = if (isPemasukanClicked) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
            )
        }

        Button(
            onClick = {
                isPengeluaranClicked = true
                isPemasukanClicked = false
                isTabunganClicked = false
                navController.navigate(Screen.Home.route)
            },
            colors = ButtonDefaults.buttonColors(if (isPengeluaranClicked) Color(0xFFDB5A5A) else Color.White),
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Pengeluaran",
                color = if (isPengeluaranClicked) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
            )
        }

        Button(
            onClick = {
                isTabunganClicked = true
                isPemasukanClicked = false
                isPengeluaranClicked = false

            },
            colors = ButtonDefaults.buttonColors(if (isTabunganClicked) Color(0xFFFAC36A) else Color.White),
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Tabungan",
                color = if (isTabunganClicked) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
            )
        }
    }
}
@Composable
fun ListItemTabungan(tabunganscreen: Tabunganscreen, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),  // Warna latar belakang putih
        elevation = CardDefaults.cardElevation(8.dp)  // Elevasi bayangan
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = tabunganscreen.namatabungan,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TabunganPreview() {
    ERITMOBILETheme {
        Tabungan(rememberNavController())
    }
}