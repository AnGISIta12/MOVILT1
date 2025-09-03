package com.example.taller1_angy_bautista.pantallas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantallaTriqui(navController: NavController) {
    val tablero = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") } //Tablero con espacios en blanco para empezar
    var jugador by remember { mutableStateOf("X") }//Empieza con X cuando empiece
    var ganador by remember { mutableStateOf<List<Int>?>(null) } //Empieza sin ganador hasta que empiece los movimientos
    Column(
        modifier = Modifier.fillMaxSize(), //Ocupa toda la pantalla
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Turno: $jugador", fontSize = 30.sp, color = Color.Red) //Color del titulo y tamaño
        // Tablero 3x3 para el juego
        for (fila in 0..2) { //https://kotlinlang.org/docs/ranges.html#progression
            Row {
                for (col in 0..2) {
                    val index = fila * 3 + col //donde se va a ubicar
                    Button(
                        onClick = {
                            if (tablero[index].isEmpty() && ganador == null) { //Si el tablero esta vacio y no hay ganador
                                tablero[index] = jugador //se va a ubicar el jugador
                                ganador = verificarGanador(tablero) //verifica si hay ganador con la funcion de verificarGanador
                                if (ganador == null) { //Si no hay ganador, se va a cambiar el jugador
                                    jugador =
                                        if (jugador == "X") "O" else "X" //Cambia el jugador
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .size(80.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (ganador?.contains(index) == true) Color.Green else Color.Blue //Color del boton y verifica si hay ganador con el index
                        )
                    ) {
                        Text(tablero[index], fontSize = 30.sp)
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp)) //Espacio entre el tablero y el boton
        Button(onClick = { //Boton para reiniciar el juego
            for (i in tablero.indices) tablero[i] = "" //Reinicia el tablero
            jugador = "X"
            ganador = null
        }) {
            Text("Reiniciar Triqui")
        }
    }
}
fun verificarGanador(tablero: List<String>): List<Int>? {
    val lineas = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
        listOf(0, 4, 8), listOf(2, 4, 6) //lista de todas las posibilidades
    )
    for (linea in lineas) { //Recorre las lineas
        val (a, b, c) = linea //Desestructura la linea
        if (tablero[a].isNotEmpty() && tablero[a] == tablero[b] && tablero[b] == tablero[c]) { //Verifica si hay ganador con las lineas a y b y c
            return linea //Si hay ganador, retorna la linea
        }
    }
    return null
}
@Preview (showBackground = true)
@Composable
fun PantallaTriquiPreview() {
    val navController = rememberNavController()
    PantallaTriqui(navController)
}