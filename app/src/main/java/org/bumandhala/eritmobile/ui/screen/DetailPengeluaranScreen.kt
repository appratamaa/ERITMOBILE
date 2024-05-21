package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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

const val KEY_ID_PENGELUARAN ="idCatatan"

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


    LaunchedEffect(true) {
        if (idPengeluaran == null) return@LaunchedEffect
        val data = viewModel.getCatatanPengeluaran(idPengeluaran) ?: return@LaunchedEffect
        tanggal = data.tanggal
        nominal = data.nominal
        keterangan = data.keterangan
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
    ) { padding ->
        FormPengeluaran(
            tanggal = tanggal,
            onTanggalChange = { tanggal = it },
            nominal = nominal,
            onNominalChange = { nominal = it },
            keterangan = keterangan,
            onKeteranganChange = { keterangan = it },
            navController = navController, // Sertakan NavController di sini
            idPengeluaran = idPengeluaran, // Sertakan idPengeluaran di sini
            viewModel = viewModel, // Sertakan viewModel di sini
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPengeluaran(
    tanggal: String, onTanggalChange: (String) -> Unit,
    nominal: Int, onNominalChange: (Int) -> Unit,
    keterangan: String, onKeteranganChange: (String) -> Unit,
    navController: NavHostController, // Tambahkan parameter navController
    idPengeluaran: Long? = null, // Tambahkan parameter id
    viewModel: DetailViewModel,
    modifier: Modifier
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var showDialog by remember { mutableStateOf(false) } // Variabel State

    // Dropdown related states
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Makan", "Tagihan", "Transportasi", "Hiburan", "Lainnya")
    var selectedOption by remember { mutableStateOf(keterangan) }
    var lainnyaText by remember { mutableStateOf("") }

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

        // ExposedDropdownMenuBox for keterangan
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
                    .menuAnchor() // Used for menu alignment
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
                            onKeteranganChange(selectionOption) // Update the state in viewModel
                            expanded = false
                            // If "Lainnya" is selected, reset the lainnyaText
                            if (selectionOption != "Lainnya") {
                                lainnyaText = ""
                            }
                        }
                    )
                }
            }
        }

        // Show TextField only when "Lainnya" is selected
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
                val finalKeterangan = if (selectedOption == "Lainnya") lainnyaText else selectedOption
                if (tanggal.isEmpty() || nominal == 0 || finalKeterangan.isEmpty()) {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                } else {
                    if (idPengeluaran == null) {
                        viewModel.insertPengeluaran(tanggal, nominal, finalKeterangan)
                    } else {
                        viewModel.updatePengeluaran(idPengeluaran, tanggal, nominal, finalKeterangan)
                    }
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
            colors = ButtonDefaults.buttonColors(Color(0xFF20BCCB)) // Mengatur warna tombol menjadi biru
        ) {
            Text(
                text = stringResource(R.string.simpan),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
                fontSize = 18.sp
            )
        }

        if (idPengeluaran != null) {
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
                colors = ButtonDefaults.buttonColors(Color(0xFF263AA2)) // Mengatur warna tombol menjadi biru
            ) {
                Text(
                    text = stringResource(R.string.tombol_hapus),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
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


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailPengeluaranScreenPreview() {
    ERITMOBILETheme {
        DetailPengeluaranScreen(rememberNavController())
    }
}