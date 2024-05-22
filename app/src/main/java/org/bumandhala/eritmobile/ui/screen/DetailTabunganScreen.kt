package org.bumandhala.eritmobile.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.CatatanDb
import org.bumandhala.eritmobile.database.TabunganDb
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactoryCatatan
import org.bumandhala.eritmobile.util.ViewModelFactoryTabungan
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val KEY_ID_TABUNGAN ="idTabungan"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTabunganScreen(navController: NavHostController, id: Long? = null) {
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
                        Text(text = stringResource(id = R.string.tambah_tabungan), color = Color(0xFF20BCCB))
                    else
                        Text(text = stringResource(id = R.string.edit_pemasukan), color = Color(0xFF20BCCB))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        FormTabungan(
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
            modifier = Modifier.padding(padding)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormTabungan(
    namatabungan: String, onnamatabunganChange: (String) -> Unit,
    targettabungan: Int, ontargettabunganChange: (Int) -> Unit,
    rencanapengisian: Int, onrencanapengisianChange: (Int) -> Unit,
    nominalpengisian: Int, onnominalpengisianChange: (Int) -> Unit,
    navController: NavHostController, // Tambahkan parameter navController
    id: Long? = null, // Tambahkan parameter id
    viewModel: DetailViewModelTabungan,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) } // Variabel State

        OutlinedTextField(
            value = namatabungan,
            onValueChange = { onnamatabunganChange(it) },
            label = { Text(text = stringResource(R.string.nama_tabungan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = modifier
                .fillMaxWidth()

        )
        OutlinedTextField(
            value = targettabungan.toString(),
            onValueChange = { newValue -> newValue.toIntOrNull()?.let { ontargettabunganChange(it) } }, // Ubah String menjadi Int jika valid
            label = { Text(text = stringResource(R.string.target_tabungan)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = rencanapengisian.toString(),
            onValueChange = { newValue -> newValue.toIntOrNull()?.let { onrencanapengisianChange(it) } }, // Ubah String menjadi Int jika valid
            label = { Text(text = stringResource(R.string.rencana_pengisian)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nominalpengisian.toString(),
            onValueChange = { newValue -> newValue.toIntOrNull()?.let { onnominalpengisianChange(it) } }, // Ubah String menjadi Int jika valid
            label = { Text(text = stringResource(R.string.nominal_pengisian)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (namatabungan.isEmpty() || targettabungan == 0 || rencanapengisian == 0 || nominalpengisian == 0) {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                } else {
                    if (id == null) {
                        viewModel.insert(namatabungan, targettabungan, rencanapengisian, nominalpengisian)
                    } else {
                        viewModel.update(id, namatabungan, targettabungan, rencanapengisian, nominalpengisian)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TabunganScreenPreview() {
    ERITMOBILETheme {
        DetailTabunganScreen(rememberNavController())
    }
}