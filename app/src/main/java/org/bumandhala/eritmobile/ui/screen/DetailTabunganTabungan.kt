package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.os.Build
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import org.bumandhala.eritmobile.util.ViewModelFactoryTabunganScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val KEY_ID_TABUNGAN ="idTabungan"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTabunganTabungan(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = TabunganScreenDb.getInstance(context)
    val factory = ViewModelFactoryTabunganScreen(db.dao)
    val viewModel: DetailViewModelTabunganScreen = viewModel(factory = factory)

    var tanggaltabungan by remember { mutableStateOf("") }
    var namaTabungan by remember { mutableStateOf("") }
    var targetTabungan by remember { mutableIntStateOf(0) } // Ubah tipe data nominal menjadi Int
    var rencanaPengisian by remember { mutableIntStateOf(0) }
    var nominalPengisian by remember { mutableIntStateOf(0) }
    var rentangwaktu by remember { mutableStateOf("") }
    var tambahtabungan by remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTabunganScreen(id) ?: return@LaunchedEffect
        tanggaltabungan = data.tanggaltabungan
        namaTabungan = data.namatabungan
        targetTabungan = data.targettabungan
        rencanaPengisian = data.rencanapengisian
        nominalPengisian = data.nominalpengisian
        rentangwaktu = data.rentangwaktu
        tambahtabungan = data.tambahtabungan
    }
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember {
        currentDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    }
    val tabunganscreen = Tabunganscreen(
        tanggaltabungan = tanggaltabungan,
        namatabungan = namaTabungan,
        targettabungan = targetTabungan,
        rencanapengisian = rencanaPengisian,
        nominalpengisian = nominalPengisian,
        rentangwaktu = rentangwaktu,
        tambahtabungan = tambahtabungan
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.kembali),
                        tint = Color(0xFF20BCCB)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.appname),
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
            modifier = Modifier.fillMaxWidth()
        )

        FormTabunganTabungan(
            tanggaltabungan = tanggaltabungan,
            ontanggaltabunganChange = { tanggaltabungan = it },
            namatabungan = namaTabungan,
            onnamatabunganChange = { namaTabungan = it },
            targettabungan = targetTabungan,
            ontargettabunganChange = { targetTabungan = it },
            rencanapengisian = rencanaPengisian,
            onrencanapengisianChange = { rencanaPengisian = it },
            nominalpengisian = nominalPengisian,
            onnominalpengisianChange = { nominalPengisian = it },
            rentangwaktu = rentangwaktu,
            onrentangwaktuChange = { rentangwaktu = it },
            tambahtabungan = tambahtabungan,
            ontambahtabunganChange = { tambahtabungan = it },
            navController = navController,
            id = id,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            tabunganscreen = tabunganscreen,
            currentDate = formattedDate
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormTabunganTabungan(
    tanggaltabungan: String, ontanggaltabunganChange: (String) -> Unit,
    namatabungan: String, onnamatabunganChange: (String) -> Unit,
    targettabungan: Int, ontargettabunganChange: (Int) -> Unit,
    rencanapengisian: Int, onrencanapengisianChange: (Int) -> Unit,
    nominalpengisian: Int, onnominalpengisianChange: (Int) -> Unit,
    rentangwaktu: String, onrentangwaktuChange: (String) -> Unit,
    tambahtabungan: Int, ontambahtabunganChange: (Int) -> Unit,
    navController: NavHostController, // Tambahkan parameter navController
    id: Long? = null, // Tambahkan parameter id
    modifier: Modifier,
    tabunganscreen: Tabunganscreen,
    currentDate: String
) {
    Column(
        modifier = modifier
            .offset(y = (-16).dp)
            .background(color = Color(0xFFEEEFF4))
            .fillMaxSize()
            ,
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAC36A)),
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tabunganscreen.namatabungan,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    ),

                    )
            }
        }
        val context = LocalContext.current
        val db = TabunganScreenDb.getInstance(context)
        val factory = ViewModelFactoryTabunganScreen(db.dao)
        val viewModel: MainViewModelTabunganScreen = viewModel(factory = factory)
        val data by viewModel.data.collectAsState()
        Column(
            modifier = modifier
                .background(color = Color(0xFFFAC36A), shape = RoundedCornerShape(25.dp))
                .fillMaxSize(),
        ) {
            Text(
                text = "Rp ${formatRupiah(targettabungan)}",
                modifier = Modifier.padding(start = 26.dp, top = 16.dp, bottom = 2.dp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Column(
                modifier = modifier
                    .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(R.string.tanggal_dibuat) + "                                ${(tanggaltabungan)}",
                    modifier = Modifier.padding(start = 26.dp, top = 24.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.nominal_perbulan) + "                           Rp ${formatRupiah(nominalpengisian)}",
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.estimasi_selesai) + "                             ${(rencanapengisian)} ${(rentangwaktu)} ",
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.tanggal_berakhir),
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp
                )
                Text(
                    text = "" + stringResource(R.string.total_tabungan) + "                                " + stringResource(
                        R.string.sisa
                    ),
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rp ${formatRupiah(tambahtabungan)}",
                        color = Color(0xFF578F52),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${(targettabungan - tambahtabungan)}",
                        color = Color(0xFFD84141),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(0.67f)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(100.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAC36A)),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.riwayat_tabungan),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 20.sp
                            ),
                        )
                    }
                }

                        if (data.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                    .background(color = Color.White)
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
                                Text(
                                    text = stringResource(R.string.tanggal_menabung) + "                        " + stringResource(
                                        R.string.nominal
                                    ),
                                    modifier = Modifier.padding(start = 16.dp, top = 12.dp),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                        }

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentPadding = PaddingValues(bottom = 84.dp)
                    ) {
                        items(data) {
                            ListItemTambahtabungan(tabunganscreen = it) {
                            }
                        }
                    }

                }
                val context = LocalContext.current
                val db = TabunganScreenDb.getInstance(context)
                val factory = ViewModelFactoryTabunganScreen(db.dao)
                val viewModel: DetailViewModelTabunganScreen = viewModel(factory = factory)
                var showDialog by remember { mutableStateOf(false) } // Variabel State
                Spacer(modifier = Modifier.height(16.dp)) // Spacer untuk memberikan ruang di antara konten dan tombol
                // Tombol Simpan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.Tambahtabungan.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.49f)
                            .padding(8.dp), // Mengisi 45% lebar layar
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFAC36A))
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null
                        )// Memberikan jarak antara ikon dan teks
                        Text(
                            text = stringResource(id = R.string.tambah_tabungan),
                            modifier = Modifier.padding(
                                vertical = 2.dp
                            ), // Mengurangi padding
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    if (id != null) {
                        Button(
                            onClick = {
                                showDialog = true
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .padding(8.dp), // Mengisi 45% lebar layar
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFD84141)) // Mengatur warna tombol menjadi merah
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(id = R.string.hapus_tabungan),
                                modifier = Modifier.padding(
                                    vertical = 2.dp
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                            DisplayAlertDialog(
                                openDialog = showDialog,
                                onDismissRequest = { showDialog = false }) {
                                showDialog = false
                                viewModel.delete(id)
                                navController.popBackStack()
                            }
                    }
                }
            }
            }
        }

}
@Composable
fun ListItemTambahtabungan(tabunganscreen: Tabunganscreen, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = tabunganscreen.tanggaltabungan,
            modifier = Modifier
                .weight(1f)
                .padding(start = 40.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
        )
        Text(
            text = "Rp ${formatRupiah(tabunganscreen.tambahtabungan)}",
            modifier = Modifier
                .weight(1f)
                .padding(start = 62.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TabunganTabunganPreview() {
    ERITMOBILETheme {
        DetailTabunganTabungan(rememberNavController())
    }
}