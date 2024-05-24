package org.bumandhala.eritmobile.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.CatatanDb
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactory
import coil.compose.rememberAsyncImagePainter

const val KEY_ID_PENGELUARAN = "idCatatan"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPengeluaranScreen(navController: NavHostController, idPengeluaran: Long? = null) {
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var tanggal by remember { mutableStateOf("") }
    var nominal by remember { mutableIntStateOf(0) } // Ubah tipe data nominal menjadi Int
    var keterangan by remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf<String?>(null) } // Tambahkan variabel untuk path gambar

    LaunchedEffect(true) {
        if (idPengeluaran == null) return@LaunchedEffect
        val data = viewModel.getCatatanPengeluaran(idPengeluaran) ?: return@LaunchedEffect
        tanggal = data.tanggal
        nominal = data.nominal
        keterangan = data.keterangan
        imagePath = data.imagePath // Muat path gambar jika ada
    }

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
                    if (idPengeluaran == null)
                        Text(text = stringResource(R.string.tambah_pengeluaran), color = Color(0xFF20BCCB))
                    else
                        Text(text = stringResource(R.string.edit_pengeluaran), color = Color(0xFF20BCCB))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) {
        FormPengeluaran(
            tanggal = tanggal,
            onTanggalChange = { tanggal = it },
            nominal = nominal,
            onNominalChange = { nominal = it },
            keterangan = keterangan,
            onKeteranganChange = { keterangan = it },
            imagePath = imagePath,
            onImagePathChange = { imagePath = it },
            navController = navController, // Sertakan NavController di sini
            idPengeluaran = idPengeluaran, // Sertakan idPengeluaran di sini
            viewModel = viewModel, // Sertakan viewModel di sini
            modifier = Modifier.padding(top = 36.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPengeluaran(
    tanggal: String, onTanggalChange: (String) -> Unit,
    nominal: Int, onNominalChange: (Int) -> Unit,
    keterangan: String, onKeteranganChange: (String) -> Unit,
    imagePath: String?, onImagePathChange: (String) -> Unit,
    navController: NavHostController,
    idPengeluaran: Long? = null,
    viewModel: DetailViewModel,
    modifier: Modifier
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Makan", "Tagihan", "Transportasi", "Hiburan", "Lainnya")
    var selectedOption by remember { mutableStateOf(keterangan) }
    var lainnyaText by remember { mutableStateOf("") }

    // Mendapatkan total pemasukan dan pengeluaran
    var totalPemasukan by remember { mutableIntStateOf(0) }
    var totalPengeluaran by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        totalPemasukan = viewModel.getTotalPemasukan()
        totalPengeluaran = viewModel.getTotalPengeluaran()
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // Lakukan sesuatu dengan URI foto yang dipilih
            val filePath = getImageFilePath(context, it)
            // Lakukan sesuatu dengan path berkas foto, seperti menyimpannya ke database
            filePath?.let { onImagePathChange(it) }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = tanggal,
            onValueChange = { onTanggalChange(it) },
            label = { Text(text = stringResource(R.string.tanggal)) },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showDatePicker(context, onTanggalChange)
                    }
                }
        )
        OutlinedTextField(
            value = if (nominal == 0) "" else nominal.toString(),
            onValueChange = { newValue ->
                val newIntValue = newValue.toIntOrNull()
                if (newIntValue != null) {
                    onNominalChange(newIntValue)
                } else {
                    if (newValue.isEmpty()) {
                        onNominalChange(0)
                    }
                }
            },
            label = { Text(text = stringResource(R.string.nominal)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(R.string.keterangan)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOption = selectionOption
                            onKeteranganChange(selectionOption)
                            expanded = false
                            if (selectionOption != "Lainnya") {
                                lainnyaText = ""
                            }
                        }
                    )
                }
            }
        }

        if (selectedOption == "Lainnya") {
            OutlinedTextField(
                value = lainnyaText,
                onValueChange = { lainnyaText = it },
                label = { Text(text = stringResource(R.string.keterangan_lainnya)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                galleryLauncher.launch("image/*") // Buka galeri ketika tombol diklik
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Unggah Foto Struk")
        }

        imagePath?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        Button(
            onClick = {
                val finalKeterangan = if (selectedOption == "Lainnya") lainnyaText else selectedOption
                if (tanggal.isEmpty() || nominal == 0 || finalKeterangan.isEmpty()) {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                } else {
                    if (totalPengeluaran + nominal > totalPemasukan) {
                        Toast.makeText(context, "Pengeluaran melebihi pemasukan!", Toast.LENGTH_LONG).show()
                    } else {
                        if (idPengeluaran == null) {
                            viewModel.insertPengeluaran(tanggal, nominal, finalKeterangan, imagePath)
                        } else {
                            viewModel.updatePengeluaran(idPengeluaran, tanggal, nominal, finalKeterangan, imagePath)
                        }
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(Color(0xFF20BCCB))
        ) {
            Text(
                text = stringResource(R.string.simpan),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        if (idPengeluaran != null) {
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(Color(0xFF263AA2))
            ) {
                Text(
                    text = stringResource(R.string.tombol_hapus),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            DisplayAlertDialog(
                openDialog = showDialog,
                onDismissRequest = { showDialog = false }) {
                showDialog = false
                viewModel.deletePengeluaran(idPengeluaran)
                navController.popBackStack()
            }
        }
    }
}

fun getImageFilePath(context: Context, uri: Uri): String? {
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
    cursor?.moveToFirst()
    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
    val filePath = columnIndex?.let { cursor.getString(it) }
    cursor?.close()
    return filePath
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailPengeluaranScreenPreview() {
    ERITMOBILETheme {
        DetailPengeluaranScreen(rememberNavController())
    }
}
