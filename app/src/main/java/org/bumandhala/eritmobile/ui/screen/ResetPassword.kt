package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.database.EritDb
import org.bumandhala.eritmobile.model.RegisterViewModel
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactory
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))

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
                        text = stringResource(id = R.string.app_name),
                        fontFamily = poppinsblack,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF20BCCB),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 38.dp)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) {padding ->
        Surface(color = Color.White) {
        ResetContent(
            modifier = Modifier
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.tertiary), navController
        )
    }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResetContent(modifier: Modifier, navController: NavHostController) {
    val poppinsextrabold = FontFamily(Font(R.font.poppinsextrabold))
    val poppinsmedium = FontFamily(Font(R.font.poppinsmedium))
    val poppinsbold = FontFamily(Font(R.font.poppinsbold))

    val context = LocalContext.current
    val db = EritDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: RegisterViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .background(color = Color(0xFFE5E7EE))
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.reset_pass),
            fontSize = 24.sp,
            fontFamily = poppinsextrabold,
            modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email),
                fontFamily = poppinsmedium) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text(stringResource(R.string.new_pass),
                fontFamily = poppinsmedium) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.konfirmasi),
                fontFamily = poppinsmedium) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
                if (newPassword == confirmPassword) {
                    coroutineScope.launch {
                        val resetSuccessful = viewModel.resetPassword(email, newPassword)
                        if (resetSuccessful) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.reset_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate back to the login screen
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else Toast.makeText(
                            context,
                            context.getString(R.string.invalid_reset),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else Toast.makeText(
                    context,
                    context.getString(R.string.invalid_pass),
                    Toast.LENGTH_SHORT
                ).show()

        },
            shape = MaterialTheme.shapes.small, // Mengatur shape menjadi lebih sedikit lengkung
            colors = ButtonDefaults.buttonColors(Color(0xFF263AA2)) // Mengatur warna tombol menjadi biru
        ) {
            Text(
                text = stringResource(id = R.string.reset_password),
                modifier = Modifier.padding(horizontal = 92.dp),
                fontFamily = poppinsextrabold,
                fontSize = 16.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ResetPasswordPreview() {
    ERITMOBILETheme {
        ResetPassword(rememberNavController())
    }
}