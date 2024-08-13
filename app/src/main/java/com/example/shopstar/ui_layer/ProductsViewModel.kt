package com.example.shopstar.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopstar.Result
import com.example.shopstar.data.Model.Product
import com.example.shopstar.data.ProductRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(private val productRepository: ProductRepository): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    
    private  val _showError = Channel<Boolean>()
    val showError = _showError.receiveAsFlow()

    init {
        viewModelScope.launch {
            productRepository.getProducts().collectLatest{
                when(it){
                    is Result.Error -> {

                    }
                    is Result.Success -> {
                        it.data?.let{
                            _products.update {
                                it
                            }
                           
                        }
                    }
                }
        } }
    }

}