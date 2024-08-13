package com.example.shopstar.data

import com.example.shopstar.data.Model.Products
import retrofit2.http.GET

interface ApiServices {

//    https://dummyjson.com/products
    @GET("products")
    suspend fun getProducts(): Products

    companion object{
        const val Base_URL = "https://dummyjson.com/"
    }

}