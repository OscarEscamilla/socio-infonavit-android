package com.example.infonavit.vo

import com.example.infonavit.data.network.WebService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// se crea como objeto para incializar solo una vez
object RetrofitClient {

    private var BASE_URL = "https://staging.api.socioinfonavit.io/api/v1/"
    // inicializamos con lazy para inicializar solo cuando se necesite
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // usar como converter a GSon
            .build()
            .create(WebService::class.java)
    }

}