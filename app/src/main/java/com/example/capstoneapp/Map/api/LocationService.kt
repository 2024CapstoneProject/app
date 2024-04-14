package com.example.capstoneapp.Map.api

import com.google.android.gms.common.api.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
interface LocationService {
    @POST("/api/locations")
    fun updateLocation(@Body locationData: LocationData): Call<Void>


}




object RetrofitClient {
    private const val BASE_URL = "http://118.67.135.211:9000"

    val instance: LocationService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(LocationService::class.java)
    }
}

data class LocationData(
    val userId: Long,
    val latitude: Double,
    val longitude: Double
)