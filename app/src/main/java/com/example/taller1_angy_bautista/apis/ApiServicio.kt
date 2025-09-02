package com.example.taller1_angy_bautista.apis

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicio {
    @GET("drivers")
    suspend fun getDrivers(@Query("session_key") sessionKey: Int): List<Driver>
}
