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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.TabunganDb
import org.bumandhala.eritmobile.model.Tabungan
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactoryTabungan

const val KEY_ID_TABUNGAN ="idTabungan"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTabunganTabungan(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = TabunganDb.getInstance(context)
    val factory = ViewModelFactoryTabungan(db.dao)
    val viewModel: DetailViewModelTabungan = viewModel(factory = factory)

    var namaTabungan by remember { mutableStateOf("") }
    var targetTabungan by remember { mutableIntStateOf(0) } // Ubah tipe data nominal menjadi Int
    var rencanaPengisian by remember { mutableIntStateOf(0) }
    var nominalPengisian by remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getTabungan(id) ?: return@LaunchedEffect
        namaTabungan = data.namatabungan
        targetTabungan = data.targettabungan
        rencanaPengisian = data.rencanapengisian
        nominalPengisian = data.nominalpengisian
    }
    val tabungan = Tabungan(
        namatabungan = namaTabungan,
        targettabungan = targetTabungan,
        rencanapengisian = rencanaPengisian,
        nominalpengisian = nominalPengisian
    )


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color(0xFF20BCCB) // Warna ikon
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
                    modifier = Modifier
                        .fillMaxWidth()
                // Menetapkan lebar penuh untuk TopAppBar
                )
        }
    ) { padding ->
        FormTabunganTabungan(
            namatabungan = namaTabungan,
            onnamatabunganChange = { namaTabungan = it },
            targettabungan = targetTabungan,
            ontargettabunganChange = { targetTabungan = it },
            rencanapengisian = rencanaPengisian,
            onrencanapengisianChange = { rencanaPengisian = it },
            nominalpengisian = nominalPengisian,
            onnominalpengisianChange = { nominalPengisian = it },
            navController = navController, // Sertakan NavController di sini
            id = id, // Sertakan id di sini
            viewModel = viewModel, // Sertakan viewModel di sini
            modifier = Modifier.padding(padding),
            tabungan = tabungan
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormTabunganTabungan(
    namatabungan: String, onnamatabunganChange: (String) -> Unit,
    targettabungan: Int, ontargettabunganChange: (Int) -> Unit,
    rencanapengisian: Int, onrencanapengisianChange: (Int) -> Unit,
    nominalpengisian: Int, onnominalpengisianChange: (Int) -> Unit,
    navController: NavHostController, // Tambahkan parameter navController
    id: Long? = null, // Tambahkan parameter id
    viewModel: DetailViewModelTabungan,
    modifier: Modifier,
    tabungan: Tabungan
) {
    Column(
        modifier = modifier
            .background(color = Color(0xFFEEEFF4))
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
                .fillMaxWidth(),
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
                    text = tabungan.namatabungan,
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
        var showDialog by remember { mutableStateOf(false) } // Variabel State
        Column(
            modifier = modifier
                .background(color = Color(0xFFFAC36A), shape = RoundedCornerShape(25.dp))
                .fillMaxSize(),
        ) {
            Text(
                text = "Rp ${formatRupiah(targettabungan)}",
                modifier = Modifier.padding(start = 26.dp, top = 16.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Column(
                modifier = modifier
                    .background(color = Color.White, shape = RoundedCornerShape(25.dp))
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(R.string.tanggal_dibuat),
                    modifier = Modifier.padding(start = 26.dp, top = 24.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.nominal_perbulan),
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.estimasi_selesai),
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
                    text =""+ stringResource(R.string.total_tabungan) + "                                      " + stringResource(R.string.sisa),
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Text(
                    text = "Rp ${formatRupiah(targettabungan)}" + "               Rp ${formatRupiah(targettabungan)}",
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color(0xFF578F52),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                Text(
                    text = stringResource(R.string.tanggal_menabung),
                    modifier = Modifier.padding(start = 26.dp, top = 6.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

//        OutlinedTextField(
//            value = namatabungan,
//            onValueChange = { onnamatabunganChange(it) },
//            label = { Text(text = stringResource(R.string.nama_tabungan)) },
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.Words,
//                imeAction = ImeAction.Next
//            ),
//            modifier = modifier
//                .fillMaxWidth()
//
//        )
//        OutlinedTextField(
//            value = targettabungan.toString(),
//            onValueChange = { newValue ->
//                newValue.toIntOrNull()?.let { ontargettabunganChange(it) }
//            }, // Ubah String menjadi Int jika valid
//            label = { Text(text = stringResource(R.string.target_tabungan)) },
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//        OutlinedTextField(
//            value = rencanapengisian.toString(),
//            onValueChange = { newValue ->
//                newValue.toIntOrNull()?.let { onrencanapengisianChange(it) }
//            }, // Ubah String menjadi Int jika valid
//            label = { Text(text = stringResource(R.string.rencana_pengisian)) },
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//        OutlinedTextField(
//            value = nominalpengisian.toString(),
//            onValueChange = { newValue ->
//                newValue.toIntOrNull()?.let { onnominalpengisianChange(it) }
//            }, // Ubah String menjadi Int jika valid
//            label = { Text(text = stringResource(R.string.nominal_pengisian)) },
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Done
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Button(
//            onClick = {
//                if (namatabungan.isEmpty() || targettabungan == 0 || rencanapengisian == 0 || nominalpengisian == 0) {
//                    Toast.makeText(context, R.string.invalid_tabungan, Toast.LENGTH_LONG).show()
//                } else {
//                    if (id == null) {
//                        viewModel.insert(
//                            namatabungan,
//                            targettabungan,
//                            rencanapengisian,
//                            nominalpengisian
//                        )
//                    }
//                    navController.popBackStack()
//                }
//            },
//            modifier = Modifier.fillMaxWidth(),
//            shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
//            colors = ButtonDefaults.buttonColors(Color(0xFF20BCCB)) // Mengatur warna tombol menjadi biru
//        ) {
//            Text(
//                text = stringResource(id = R.string.simpan),
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
//                fontSize = 18.sp
//            )
//        }
//        if (id != null) {
//            Button(
//                onClick = {
//                    showDialog = true
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
//                colors = ButtonDefaults.buttonColors(Color(0xFF263AA2)) // Mengatur warna tombol menjadi biru
//            ) {
//                Text(
//                    text = stringResource(id = R.string.tombol_hapus),
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                    fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
//                    fontSize = 18.sp
//                )
//            }
//            DisplayAlertDialog(
//                openDialog = showDialog,
//                onDismissRequest = { showDialog = false }) {
//                showDialog = false
//                viewModel.delete(id)
//                navController.popBackStack()
//            }
//        }
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