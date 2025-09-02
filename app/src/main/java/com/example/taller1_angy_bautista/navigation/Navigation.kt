package com.example.taller1_angy_bautista.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller1_angy_bautista.HomeScreen
import com.example.taller1_angy_bautista.Juego
import com.example.taller1_angy_bautista.pantallas.PantallaTriqui
import com.example.taller1_angy_bautista.pantallas.Piloto
import com.example.taller1_angy_bautista.pantallas.PilotosF1

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Juego.Home.name){
        composable(Juego.Home.name){ //pantalla de inicio
            HomeScreen(navController)
        }
        composable(Juego.Juego.name){ //pantalla del juego
            PantallaTriqui(navController)
        }
        composable(Juego.Pilotos.name){ //pantalla de los pilotos
            PilotosF1(navController)
        }
        composable("detalle_piloto/{driverNumber}") { backStackEntry ->
            val driverNumber = backStackEntry.arguments?.getString("driverNumber")?.toInt() ?: 0 // get en el número del piloto
            Piloto(navController, driverNumber)
        }
    }
}