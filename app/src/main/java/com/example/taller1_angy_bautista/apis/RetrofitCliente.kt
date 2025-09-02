package com.example.taller1_angy_bautista.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCliente {
    private const val BASE_URL = "https://api.openf1.org/v1/"

    val api: ApiServicio = Retrofit.Builder()
        .baseUrl(BASE_URL) //donde esta la API
        .addConverterFactory(GsonConverterFactory.create()) //convierte el JSON
        .build() //construye
        .create(ApiServicio::class.java) //crea el objeto que se comunica con la API
}
