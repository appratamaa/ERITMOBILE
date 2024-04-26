package org.bumandhala.eritmobile.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bumandhala.eritmobile.R
import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme

@Composable
fun Landing1(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF263AA2))
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.celengan),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
        }
        content()
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Landing1Preview() {
    ERITMOBILETheme {
        Landing1 {
        }
    }
}
