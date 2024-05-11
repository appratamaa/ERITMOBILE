package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.landingscreennavigation.Screen
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme

@Composable
fun PopupLogin(showDialog: MutableState<Boolean>, navController: NavHostController) {
    if (showDialog.value) {
        val poppinsbold = FontFamily(Font(R.font.poppinsbold))
        val poppinsextrabold = FontFamily(Font(R.font.poppinsextrabold))

        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier.background(
                Color(0xFF263AA2),
                shape = RoundedCornerShape(5))
                .width(200.dp)
                .padding(12.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.kamuberhasilmasuk),
                        fontFamily = poppinsbold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(color = Color.White, fontSize = 12.sp)
                    )
                    Image(
                        painter = painterResource(R.drawable.kamu_berhasil_masuk),
                        contentDescription = "Berhasil",
                        modifier = Modifier.size(100.dp)
                    )
                    Button(
                        onClick = {
                            showDialog.value = false
                            navController.navigate(Screen.Home.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF20BCCB)),
                        modifier = Modifier
                            .width(150.dp),
                        shape = RoundedCornerShape(15)
                    ) {
                        Text(
                            text = stringResource(R.string.tomboloke),
                            fontFamily = poppinsextrabold,
                            textAlign = TextAlign.Center,
                            style = TextStyle(color = Color.White, fontSize = 14.sp)
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogLoginPreview() {
    val showDialog = remember { mutableStateOf(false) }
    ERITMOBILETheme {
        PopupLogin(showDialog = showDialog, rememberNavController())
    }
}