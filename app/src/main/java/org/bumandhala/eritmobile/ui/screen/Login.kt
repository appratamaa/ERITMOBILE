package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.database.EritDb
import org.bumandhala.eritmobile.landingscreennavigation.Screen
import org.bumandhala.eritmobile.model.LoginViewModel
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))
    val poppinsmedium = FontFamily(Font(R.font.poppinsmedium))

    var namaPengguna by rememberSaveable { mutableStateOf("") }
    var namaPenggunaError by remember { mutableStateOf(false) }

    var kataSandi by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var enterPasswordError by remember { mutableStateOf(false) }

    val showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val db = EritDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF263AA2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 43.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logoertitmobile),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.masuk),
                fontFamily = poppinsblack,
                style = TextStyle(color = Color.White, fontSize = 26.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = namaPengguna,
                onValueChange = { namaPengguna = it },
                placeholder = { Text(text = stringResource(R.string.namapengguna),
                    style = TextStyle(color = Color(0xFFBEBEBE), fontSize = 18.sp),
                    fontFamily = poppinsmedium,) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(15),
                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                textStyle = TextStyle(fontFamily = poppinsmedium)
            )
            OutlinedTextField(
                value = kataSandi,
                onValueChange = { kataSandi = it },
                placeholder = { Text(text = stringResource(R.string.katasandi),
                    style = TextStyle(color = Color(0xFFBEBEBE), fontSize = 18.sp),
                    fontFamily = poppinsmedium,) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,

                    ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        val icon: Painter = if (passwordVisibility) {
                            painterResource(id = R.drawable.eye_off)
                        } else {
                            painterResource(id = R.drawable.eye_on)
                        }
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
                shape = RoundedCornerShape(15),
                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                textStyle = TextStyle(fontFamily = poppinsmedium),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.lupakatasandi),
                fontFamily = poppinsmedium,
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.padding(end = 150.dp).clickable {
//                    navController.navigate(Screen.Landing3.route)
                }
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { coroutineScope.launch {
                    if (viewModel.login(
                            namaPengguna,
                            kataSandi
                        )
                    ) {
                        showDialog.value = true
                    } else Toast.makeText(
                        context,
                        context.getString(R.string.login_invalid),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF20BCCB)),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15)
            ) {
                Text(
                    text = stringResource(R.string.masuk),
                    fontFamily = poppinsblack,
                    style = TextStyle(color = Color.White, fontSize = 22.sp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.belumpunyaakun),
                fontFamily = poppinsmedium,
                style = TextStyle(color = Color.White, fontSize = 18.sp),
            )
            Text(
                text = stringResource(R.string.buatakun),
                fontFamily = poppinsblack,
                style = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        if (showDialog.value) {
            PopupLogin(showDialog = showDialog, navController = navController)
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LoginPreview() {
    ERITMOBILETheme {
        Login(rememberNavController())
    }
}