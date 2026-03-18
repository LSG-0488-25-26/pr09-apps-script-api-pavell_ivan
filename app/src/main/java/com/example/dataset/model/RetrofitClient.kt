package com.example.dataset.model

import com.example.dataset.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val instance: PokemonApiService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request  = chain.request()
                val response = chain.proceed(request)
                val bodyStr  = response.peekBody(Long.MAX_VALUE).string()
                android.util.Log.d("RETROFIT_RAW", "URL: ${request.url}")
                android.util.Log.d("RETROFIT_RAW", "Body: $bodyStr")
                response
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://script.google.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)
    }
}


