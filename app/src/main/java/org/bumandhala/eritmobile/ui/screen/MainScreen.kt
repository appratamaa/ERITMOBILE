package org.bumandhala.eritmobile.ui.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import org.bumandhala.eritmobile.database.CatatanDb
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Pengeluaran
import org.bumandhala.eritmobile.navigation.Screen
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.SettingsDataStore
import org.bumandhala.eritmobile.util.ViewModelFactory
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController) {
    SettingsDataStore(LocalContext.current)

    var selectedButton by remember { mutableStateOf("Pemasukan") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF20BCCB),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = White
                ),
                modifier = Modifier
                    .fillMaxWidth() // Menetapkan lebar penuh untuk TopAppBar
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (selectedButton) {
                        "Pemasukan" -> navController.navigate(Screen.FormBaruPemasukan.route)
                        "Pengeluaran" -> navController.navigate(Screen.FormBaruPengeluaran.route)
                        // Tambahkan rute lain jika diperlukan
                    }
                },
                containerColor = Color(0xFF20BCCB) // Ganti dengan warna yang diinginkan
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24), // Panggil asset vector dari drawable
                    contentDescription = stringResource(R.string.tambah_pemasukan),
                    tint = White // Warna ikon
                )
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(8.dp).background(color = White)
            ) {
                // Menambahkan tombol pertama
                IconButton(
                    onClick = { /* Tindakan saat tombol pertama diklik */ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_home_filled_24), // Ganti dengan ikon Anda
                        contentDescription = "Beranda"
                    )
                }

                // Menambahkan tombol kedua
                IconButton(
                    onClick = { /* Tindakan saat tombol kedua diklik */ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_auto_graph_24), // Ganti dengan ikon Anda
                        contentDescription = "Grafik"
                    )
                }

                // Menambahkan tombol ketiga
                IconButton(
                    onClick = { /* Tindakan saat tombol ketiga diklik */ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24), // Ganti dengan ikon Anda
                        contentDescription = "Profil"
                    )
                }
            }
        }
    ) { padding ->
        Surface(color = White) {
            // Menampilkan konten layar utama
            ScreenContent(
                modifier = Modifier
                    .padding(padding),
                navController = navController,
                selectedButton = selectedButton,
                onSelectedButtonChange = { selectedButton = it }
            )
        }
    }
}

    fun formatRupiah(nominal: Int): String {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatRupiah.format(nominal).replace("Rp", "")
    }


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    selectedButton: String,
    onSelectedButtonChange: (String) -> Unit
) {
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val pemasukanData by viewModel.pemasukanData.collectAsState()
    val pengeluaranData by viewModel.pengeluaranData.collectAsState()

    val totalPemasukan = pemasukanData.sumOf { it.nominal }
    val totalPengeluaran = pengeluaranData.sumOf { it.nominal }
    val saldo = totalPemasukan - totalPengeluaran

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFE5E7EE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Saldo(saldo)
            Spacer(modifier = Modifier.weight(1f))
            CalendarIconAndDateTime()
        }

        Row {
            MyButtons(selectedButton) { newSelection ->
                onSelectedButtonChange(newSelection)
            }
        }

        val totalNominal = when (selectedButton) {
            "Pemasukan" -> totalPemasukan
            "Pengeluaran" -> totalPengeluaran
            else -> 0
        }
        val isEmpty = when (selectedButton) {
            "Pemasukan" -> pemasukanData.isEmpty()
            "Pengeluaran" -> pengeluaranData.isEmpty()
            else -> true
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(
                    color = when (selectedButton) {
                        "Pemasukan" -> Color(0xFF00B4FF)
                        "Pengeluaran" -> Color(0xFFDB5A5A)
                        else -> Color(0xFF00B4FF)
                    }
                )
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = selectedButton,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Text(
                    text = "Rp ${formatRupiah(totalNominal)}",
                    modifier = Modifier,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
            if (isEmpty) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .background(White)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.list_kosong))
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .background(White)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Tanggal",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Nominal",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Keterangan",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .background(White)
                        .padding(16.dp, 0.dp, 16.dp, 16.dp),
                    contentPadding = PaddingValues(bottom = 84.dp)
                ) {
                    if (selectedButton == "Pemasukan") {
                        items(pemasukanData) { pemasukan ->
                            ListPemasukan(pemasukan = pemasukan) {
                                navController.navigate(
                                    Screen.FormUbahPemasukan.withIdPemasukan(pemasukan.idPemasukan)
                                )
                            }
                        }
                    } else if(selectedButton == "Pengeluaran") {
                        items(pengeluaranData) { pengeluaran ->
                            ListPengeluaran(pengeluaran = pengeluaran) {
                                navController.navigate(
                                    Screen.FormUbahPengeluaran.withIdPengeluaran(pengeluaran.idPengeluaran)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun MyButtons(selectedButton: String, onSelectionChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
            .background(color = White, shape = RoundedCornerShape(50))
            .height(39.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onSelectionChange("Pemasukan") },
            colors = ButtonDefaults.buttonColors(if (selectedButton == "Pemasukan") Color(0xFF00B4FF) else White),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Pemasukan",
                color = if (selectedButton == "Pemasukan") White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = { onSelectionChange("Pengeluaran") },
            colors = ButtonDefaults.buttonColors(if (selectedButton == "Pengeluaran") Color(0xFFDB5A5A) else White),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Pengeluaran",
                color = if (selectedButton == "Pengeluaran") White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = { onSelectionChange("Tabungan") },
            colors = ButtonDefaults.buttonColors(if (selectedButton == "Tabungan") Color(0xFFFAC36A) else White),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Tabungan",
                color = if (selectedButton == "Tabungan") White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun Saldo(saldo: Int) {
    val textSaldo = "Saldo anda "
    val jumlahSaldo = "Rp ${formatRupiah(saldo)}"

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(White)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textSaldo,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = jumlahSaldo,
            color = Color(0xFF20BCCB),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarIconAndDateTime() {
    val currentDateTime = LocalDateTime.now()
    val formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Calendar Icon",
            modifier = Modifier
                .padding(start = 6.dp)
                .size(18.dp),
        )
        Text(
            text = formattedDateTime,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ListPemasukan(pemasukan: Pemasukan, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(White)
            .padding(16.dp)
    ) {
        Text(
            text = pemasukan.tanggal,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            text = pemasukan.nominal.toString(),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            text = pemasukan.keterangan,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListPengeluaran(pengeluaran: Pengeluaran, onClick: () -> Unit) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(White)
            .padding(16.dp)
    ) {
        Text(
            text = pengeluaran.tanggal,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            text = pengeluaran.nominal.toString(),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            text = pengeluaran.keterangan,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Icon(
            painter = painterResource(R.drawable.baseline_library_books_24),
            contentDescription = "Buka Struk",
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    pengeluaran.imagePath?.let { imagePath ->
                        // Tampilkan dialog saat ikon diklik
                        showDialog.value = true
                    }
                }
        )
    }

    if (showDialog.value) {
        ShowStrukDialog(imagePath = pengeluaran.imagePath ?: "") {
            showDialog.value = false
        }
    }
}

@Composable
fun ShowStrukDialog(imagePath: String, onClose: () -> Unit) {
    val bitmap = loadImageFromPath(imagePath)

    AlertDialog(
        onDismissRequest = { onClose() },
        title = { Text(text = "Struk") },
        text = {
            bitmap?.let {
                val imageBitmap = it.asImageBitmap()
                Image(bitmap = imageBitmap, contentDescription = "Gambar Struk")
            }
        },
        confirmButton = {
            Button(
                onClick = { onClose() }
            ) {
                Text(text = "OK")
            }
        }
    )
}


fun loadImageFromPath(path: String): Bitmap? {
    return try {
        BitmapFactory.decodeFile(path)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    ERITMOBILETheme {
        MainScreen(rememberNavController())
    }
}
