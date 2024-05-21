package org.bumandhala.eritmobile.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
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
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_pemasukan),
                    tint = Color(0xFF20BCCB)
                )
            }
        }
    ) { padding ->
        Surface(color = White) {
            // Menampilkan konten layar utama
            ScreenContent(
                modifier = Modifier
                    .padding(padding)
                    .background(color = MaterialTheme.colorScheme.tertiary),
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
                horizontalArrangement = Arrangement.Center, // Menetapkan horizontal alignment ke tengah
                verticalAlignment = Alignment.CenterVertically // Menetapkan vertikal alignment ke tengah
            ) {
                Salutation()
                Spacer(modifier = Modifier.weight(1f)) // Spacer untuk memberikan ruang di antara elemen
                CalendarIconAndDateTime()
            }

            // Menampilkan tombol-tombol
            Row {
                MyButtons(selectedButton) { newSelection ->
                    onSelectedButtonChange(newSelection)
                } // Memanggil fungsi MyButtons yang telah dibuat sebelumnya
            }

            // Menampilkan kotak "Pemasukan" atau "Pengeluaran"
            val totalNominal = when (selectedButton) {
                "Pemasukan" -> pemasukanData.sumOf { it.nominal } - pengeluaranData.sumOf { it.nominal }
                "Pengeluaran" -> pengeluaranData.sumOf { it.nominal }
                else -> 0
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .background(
                        color = when (selectedButton) {
                            "Pemasukan" -> Color(0xFF00B4FF)
                            "Pengeluaran" -> Color(0xFFDB5A5A)
                            "Tabungan" -> Color(0xFFFAC36A)
                            else -> Color(0xFF00B4FF)
                        }
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = selectedButton,
                    modifier = Modifier.padding(16.dp, 16.dp, 8.dp), // Sesuaikan padding di sini
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Text(
                    text = "Rp ${formatRupiah(totalNominal)}",
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }

            val isEmpty = when (selectedButton) {
                "Pemasukan" -> pemasukanData.isEmpty()
                "Pengeluaran" -> pengeluaranData.isEmpty()
                else -> true
            }

            if (isEmpty) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
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
                        .background(color = White)
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
                        .background(color = White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentPadding = PaddingValues(bottom = 84.dp)
                ) {
                    if (selectedButton == "Pemasukan") {
                        items(pemasukanData) { pemasukan ->
                            ListPemasukan(pemasukan = pemasukan) {
                                navController.navigate(
                                    Screen.FormUbahPemasukan.withIdPemasukan(
                                        pemasukan.idPemasukan
                                    )
                                )
                            }
                        }
                    } else if (selectedButton == "Pengeluaran") {
                        items(pengeluaranData) { pengeluaran ->
                            ListPengeluaran(pengeluaran = pengeluaran) {
                                navController.navigate(
                                    Screen.FormUbahPengeluaran.withIdPengeluaran(
                                        pengeluaran.idPengeluaran
                                    )
                                )
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
            .padding(top = 0.dp, bottom = 20.dp, start = 16.dp, end = 16.dp) // Atur padding di sini
            .background(color = White, shape = RoundedCornerShape(50))
            .height(39.dp), // Bentuk tombol
        horizontalArrangement = Arrangement.SpaceBetween, // Atur penempatan tombol secara horizontal
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
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
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
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
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
                fontSize = 12.sp // Mengatur ukuran font menjadi lebih kecil
            )
        }
    }
}

@Composable
fun Salutation() {
    val salutation = "Hai, User!"
    Text(
        text = salutation,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        fontWeight = FontWeight.Bold,

    )
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
            modifier = Modifier.padding(start = 6.dp).size(18.dp),
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
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
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
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
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
