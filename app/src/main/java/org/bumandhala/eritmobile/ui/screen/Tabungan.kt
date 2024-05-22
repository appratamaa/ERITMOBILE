package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.CatatanDb
import org.bumandhala.eritmobile.database.TabunganDb
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Tabungan
import org.bumandhala.eritmobile.navigation.Screen
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.SettingsDataStore
import org.bumandhala.eritmobile.util.ViewModelFactoryCatatan
import org.bumandhala.eritmobile.util.ViewModelFactoryTabungan

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tabungan(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))
    var showDialog by remember { mutableStateOf(false) }

    SettingsDataStore(LocalContext.current)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.appname),
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
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.tambah_tabungan),
                    tint = Color(0xFF20BCCB)
                )
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
    val db = TabunganDb.getInstance(context)
    val factory = ViewModelFactoryTabungan(db.dao)
    val viewModel: MainViewModelTabungan = viewModel(factory = factory)
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
            Salutation()
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
                    ListItemTabungan(tabungan = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
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
fun ListItemTabungan(tabungan: Tabungan, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp),
                )
                .graphicsLayer(
                    shadowElevation = 4f,
                    shape = RoundedCornerShape(25.dp),
                    clip = true
                )
                .padding(16.dp, 12.dp)
        ) {
            Text(
                text = tabungan.namatabungan,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TabunganPreview() {
    ERITMOBILETheme {
        Tabungan(rememberNavController())
    }
}