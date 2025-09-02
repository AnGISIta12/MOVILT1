package com.example.taller1_angy_bautista.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // No se usa, podrías removerla si no la necesitas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.taller1_angy_bautista.apis.Driver
import com.example.taller1_angy_bautista.apis.RetrofitCliente
import kotlinx.coroutines.launch
@Composable
fun Piloto(navController: NavController, driverNumber: Int) {
    val scope = rememberCoroutineScope()
    var driver by remember { mutableStateOf<Driver?>(null) }
    var carga by remember { mutableStateOf(true) }
    val countryMap = mapOf(
        "Lando NORRIS" to "GB", "Oscar PIASTRI" to "AU",
        "Lewis HAMILTON" to "GB", "George RUSSELL" to "GB", "Charles LECLERC" to "MC",
        "Carlos SAINZ" to "ES", "Fernando ALONSO" to "ES", "Lance STROLL" to "CA",
        "Pierre GASLY" to "FR", "Esteban OCON" to "FR", "Yuki TSUNODA" to "JP",
        "Guanyu ZHOU" to "CN", "Gabriel BORTOLETO" to "BR", "Isack HADJAR" to "FR",
        "Jack DOOHAN" to "AU", "Andrea Kimi ANTONELLI" to "IT", "Liam LAWSON" to "NZ",
        "Oliver BEARMAN" to "GB", "Nico HULKENBERG" to "DE", "Max VERSTAPPEN" to "DE"
    )
    LaunchedEffect(driverNumber) {
        scope.launch {
            try {
                val respuesta = RetrofitCliente.api.getDrivers(9684)
                driver = respuesta.find { it.driver_number == driverNumber }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                carga = false
            }
        }
    }
    if (carga) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (driver == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Piloto no encontrado en la lista")
        }
    } else {
        val driverValue = driver!!
        val countryCode = countryMap[driverValue.full_name] ?: "UN" // "UN" como fallback para país desconocido
        val banderaUrl = "https://flagsapi.com/$countryCode/flat/64.png"
        val teamColor = driverValue.team_colour?.let { colorHex -> hexToColor(colorHex)
        } ?: MaterialTheme.colorScheme.onSurface
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Padding alrededor de
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // También centramos el contenido del Column verticalmente
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center // Centra los elementos del Row si el Row es más ancho
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(driverValue.headshot_url),
                        contentDescription = driverValue.full_name,
                        modifier = Modifier.size(120.dp), // Tamaño de la foto del piloto
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(banderaUrl),
                        contentDescription = "Bandera de $countryCode",
                        modifier = Modifier.size(64.dp), // Tamaño de la bandera, ajusta según necesites
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    driverValue.full_name ?: "Sin nombre",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))
                Text("Número: ${driverValue.driver_number}")
                Spacer(Modifier.height(16.dp))
                Text("Equipo: ${driverValue.team_name ?: "Desconocido"}",color=teamColor)
                Spacer(Modifier.height(16.dp))
                Text("Acronym: ${driverValue.name_acronym ?: "Desconocido"}")
                Spacer(Modifier.height(32.dp))
                Button(onClick = { navController.popBackStack() }) { // Navega hacia atrás
                    Text("Volver")
                }
            }
        }
    }
}
private fun hexToColor(hexString: String?): Color {
    if (hexString == null) {
        return Color.Gray
    }
    val validatedHexString = if (hexString.startsWith("#")) hexString else "#$hexString"
    return try {
        Color(android.graphics.Color.parseColor(validatedHexString))
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        Color.Gray //POR DEFAULT
    }
}