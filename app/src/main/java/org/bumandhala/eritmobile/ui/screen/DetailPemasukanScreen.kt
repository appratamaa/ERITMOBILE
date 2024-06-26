package org.bumandhala.eritmobile.ui.screen

import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import org.bumandhala.eritmobile.util.ViewModelFactoryCatatan
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


const val KEY_ID_CATATAN ="idCatatan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemasukanScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = CatatanDb.getInstance(context)
    val factory = ViewModelFactoryCatatan(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var tanggal by remember { mutableStateOf("") }
    var nominal by remember { mutableIntStateOf(0) } // Ubah tipe data nominal menjadi Int
    var keterangan by remember { mutableStateOf("") }


    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getCatatan(id) ?: return@LaunchedEffect
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
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_pemasukan), color = Color(0xFF20BCCB))
                    else
                        Text(text = stringResource(id = R.string.edit_pemasukan), color = Color(0xFF20BCCB))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        FormCatatan(
            tanggal = tanggal,
            onTanggalChange = { tanggal = it },
            nominal = nominal,
            onNominalChange = { nominal = it },
            keterangan = keterangan,
            onKeteranganChange = { keterangan = it },
            navController = navController, // Sertakan NavController di sini
            id = id, // Sertakan id di sini
            viewModel = viewModel, // Sertakan viewModel di sini
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun FormCatatan(
    tanggal: String, onTanggalChange: (String) -> Unit,
    nominal: Int, onNominalChange: (Int) -> Unit,
    keterangan: String, onKeteranganChange: (String) -> Unit,
    navController: NavHostController, // Tambahkan parameter navController
    id: Long? = null, // Tambahkan parameter id
    viewModel: DetailViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current
        val focusRequester = remember { FocusRequester() }
        var showDialog by remember { mutableStateOf(false) } // Variabel State

        OutlinedTextField(
            value = tanggal,
            onValueChange = { onTanggalChange(it) },
            label = { Text(text = stringResource(R.string.tanggal)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
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
            value = nominal.toString(),
            onValueChange = { newValue -> newValue.toIntOrNull()?.let { onNominalChange(it) } }, // Ubah String menjadi Int jika valid
            label = { Text(text = stringResource(R.string.nominal)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = keterangan,
            onValueChange = { onKeteranganChange(it) },
            label = { Text(text = stringResource(R.string.keterangan)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (tanggal.isEmpty() || nominal == 0 || keterangan.isEmpty()) {
                    Toast.makeText(context, R.string.invalid_tabungan, Toast.LENGTH_LONG).show()
                } else {
                    if (id == null) {
                        viewModel.insert(tanggal, nominal, keterangan)
                    } else {
                        viewModel.update(id, tanggal, nominal, keterangan)
                    }
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
            colors = ButtonDefaults.buttonColors(Color(0xFF20BCCB)) // Mengatur warna tombol menjadi biru
        ) {
            Text(
                text = stringResource(id = R.string.simpan),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
                fontSize = 18.sp
            )
        }
        if (id != null) {
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
                colors = ButtonDefaults.buttonColors(Color(0xFF263AA2)) // Mengatur warna tombol menjadi biru
            ) {
                Text(
                    text = stringResource(id = R.string.tombol_hapus),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold, // Mengatur teks menjadi tebal
                    fontSize = 18.sp
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

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(context, { _, selectedYear, monthOfYear, dayOfMonth ->
        calendar.set(selectedYear, monthOfYear, dayOfMonth)
        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val selectedDate = format.format(calendar.time)
        onDateSelected(selectedDate)
    }, year, month, day).show()
}





@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    ERITMOBILETheme {
        DetailPemasukanScreen(rememberNavController())
    }
}