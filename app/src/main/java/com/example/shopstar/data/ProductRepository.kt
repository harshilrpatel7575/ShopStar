package com.example.shopstar.data

import com.example.shopstar.Result
import com.example.shopstar.data.Model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(
    private val apiServices: ApiServices
) {
    suspend fun  getProducts(): Flow<Result<List<Product>>>{
        return flow {
            val productResponse =
                try{
                    apiServices.getProducts()}
                catch(e: Exception){
                    e.printStackTrace()
                    emit(Result.Error(message = "Failed to fetch products"))
                    return@flow
                }
            emit(Result.Success(productResponse.products))
        }
    }

}