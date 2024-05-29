package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.database.EritDb
import org.bumandhala.eritmobile.model.LoginViewModel
import org.bumandhala.eritmobile.model.RegisterViewModel
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
import org.bumandhala.eritmobile.util.ViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.navigation.Screen

@Composable
fun ResetPassword(navController: NavHostController) {
    val context = LocalContext.current
    val db = EritDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: RegisterViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Reset Password", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (newPassword == confirmPassword) {
                coroutineScope.launch {
                    val resetSuccessful = viewModel.resetPassword(email, newPassword)
                    if (resetSuccessful) {
                        // Navigate back to the login screen
                        navController.navigate(Screen.Login.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Failed to reset password"
                    }
                }
            } else {
                errorMessage = "Password and confirmation do not match"
            }
        }) {
            Text("Reset Password")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = Color.Red)
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ResetPasswordPreview() {
    ERITMOBILETheme {
        ResetPassword(rememberNavController())
    }
}