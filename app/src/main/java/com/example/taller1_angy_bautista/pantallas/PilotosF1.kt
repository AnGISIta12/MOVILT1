package com.example.taller1_angy_bautista.pantallas

import androidx.compose.runtime.Composable
import com.example.taller1_angy_bautista.apis.RetrofitCliente
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.taller1_angy_bautista.apis.Driver
import kotlinx.coroutines.launch
@Composable
fun PilotosF1(navController: NavController) {
    val scope = rememberCoroutineScope()
    var drivers by remember { mutableStateOf<List<Driver>>(emptyList()) }
    var carga by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                drivers = RetrofitCliente.api.getDrivers(9684)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
               carga = false
            }
        }
    }
    if (carga) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() //para que quede centradooooooo
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(drivers) { driver ->
                PilotoD(driver, navController)
            }
        }
    }
}
@Composable
fun PilotoD(driver: Driver, navController: NavController) {
    // Mapa de pilotos a países para conectar con la api de banderas
    val countryMap = mapOf(
        "Lando NORRIS" to "GB", "Oscar PIASTRI" to "AU", "Lewis HAMILTON" to "GB", "George RUSSELL" to "GB",
        "Charles LECLERC" to "MC", "Carlos SAINZ" to "ES", "Fernando ALONSO" to "ES", "Lance STROLL" to "CA",
        "Pierre GASLY" to "FR", "Esteban OCON" to "FR", "Yuki TSUNODA" to "JP", "Guanyu ZHOU" to "CN",
        "Gabriel BORTOLETO" to "BR", "Isack HADJAR" to "FR", "Jack DOOHAN" to "AU", "Andrea Kimi ANTONELLI" to "IT",
        "Liam LAWSON" to "NZ", "Oliver BEARMAN" to "GB", "Nico HULKENBERG" to "DE", "Max VERSTAPPEN" to "DE"
    )
    val countryCode = countryMap[driver.full_name] ?: "UN" // por si no esta la bandera
    val banderaUrl = "https://flagsapi.com/$countryCode/flat/64.png"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("detalle_piloto/${driver.driver_number}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(driver.headshot_url),
                contentDescription = driver.full_name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(driver.full_name ?: "Sin nombre", style = MaterialTheme.typography.titleMedium)
                Text("Equipo: ${driver.team_name ?: "Desconocido"}")
                Text("Acronym: ${driver.name_acronym ?: "Desconocido"}")
                Text("Número: ${driver.driver_number}")
            }
            Image(
                painter = rememberAsyncImagePainter(banderaUrl),
                contentDescription = "Bandera de $countryCode",
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}