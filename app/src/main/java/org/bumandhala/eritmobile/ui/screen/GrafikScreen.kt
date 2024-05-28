//package org.bumandhala.eritmobile.ui.screen
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.CornerRadius
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.graphics.toArgb
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import org.bumandhala.eritmobile.ui.theme.ERITMOBILETheme
//
//// Di GrafikScreen.kt
//@Composable
//fun GrafikScreen(viewModel: DetailViewModel) {
//    val pengeluaranData by viewModel.getCatatanPengeluaran().collectAsState(emptyList())
//
//    val totalPengeluaran by remember {
//        viewModel.getTotalPengeluaran().collectAsState(0)
//    }
//
//    val data = pengeluaranData.map {
//        BarChartData(
//            percentage = it.nominal.toFloat() / totalPengeluaran.toFloat(),
//            color = Color(0xFFBDBDBD), // Gunakan warna sesuai kategori jika ada
//            label = it.keterangan
//        )
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        BarChartView(data = data, modifier = Modifier.weight(1f).fillMaxHeight())
//    }
//}
//
//
//data class BarChartData(
//    val percentage: Float,
//    val color: Color,
//    val label: String
//)
//
//// Di BarChartView.kt
//@Composable
//fun BarChartView(data: List<BarChartData>, modifier: Modifier) {
//    Canvas(modifier = modifier) {
//        val barHeight = size.height / data.size
//        val maxPercentage = data.maxByOrNull { it.percentage }?.percentage ?: 1f
//
//        data.forEachIndexed { index, barData ->
//            val barWidth = size.width * (barData.percentage / maxPercentage)
//            val startX = 0f
//            val startY = index * barHeight
//            val cornerRadius = 8.dp.toPx() // Adjust the corner radius
//
//            drawRoundRect(
//                color = barData.color,
//                topLeft = Offset(startX, startY),
//                size = Size(barWidth, barHeight),
//                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
//            )
//
//            drawContext.canvas.nativeCanvas.drawText(
//                barData.label,
//                barWidth + 8.dp.toPx(), // Adjust the text position
//                startY + barHeight - 8.dp.toPx(),
//                android.graphics.Paint().apply {
//                    isAntiAlias = true
//                    textSize = 16f
//                    color = Color.Black.toArgb()
//                }
//            )
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun GrafikScreenPreview() {
//    ERITMOBILETheme {
//        GrafikScreen()
//    }
//}
