package com.example.shopstar.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
private val client = OkHttpClient.Builder().build()
    val api : ApiServices= Retrofit.Builder().baseUrl(ApiServices.Base_URL).client(
        client
    ).addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices::class.java)

}