package com.example.catfacts.api

import com.example.catfacts.models.Fact
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface CatFactsApi {

    @GET("/fact")
    suspend fun getFact(): Response<Fact>

    companion object {
        private const val BASE_URL = "https://catfact.ninja"

        fun create(): CatFactsApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CatFactsApi::class.java)
        }

    }

}