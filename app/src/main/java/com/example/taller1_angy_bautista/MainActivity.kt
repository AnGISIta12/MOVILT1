package com.example.taller1_angy_bautista
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taller1_angy_bautista.navigation.Navigation
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround // Distribuye el espacio entre las imagenes
    ) { //Triqui y el boton pa eso
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.triqui),
                contentDescription = "Juego de triqui",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f) // La imagen toma el espacio
                    .fillMaxWidth()
                    .padding(bottom = 4.dp) // Espacio entre la imagen y el botón
            )
            Button(onClick = { navController.navigate(Juego.Juego.name) }) { // Navega a la pantalla de juego
                Text("Triqui")
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los dos bloques ne la clase ponemos 100pre 16
        Column( //Pilotos y el boton pa eso
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.f1),
                contentDescription = "Logo de Formula 1",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f) // La imagen toma el espacio
                    .fillMaxWidth()
                    .padding(bottom = 4.dp) // Espacio entre la imagen y el botón
            )
            Button(onClick = { navController.navigate(Juego.Pilotos.name) }) {
                Text("Ver Pilotos F1")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}