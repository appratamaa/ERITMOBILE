package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme


@Composable
fun Landing1() {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))

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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.landing1),
                fontFamily = poppinsblack,
                style = TextStyle(color = Color.White, fontSize = 26.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.celengan),
                contentDescription = null,
                modifier = Modifier.size(450.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = Color(0xFF20BCCB))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF20BCCB)),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5)
            ) {
                Text(
                    text = stringResource(R.string.lewati),
                    fontFamily = poppinsblack,
                    style = TextStyle(color = Color.White, fontSize = 26.sp),
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Landing1Preview() {
    ERITMOBILETheme {
    Landing1()
    }
}
