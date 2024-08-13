package com.example.shopstar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.shopstar.data.ApiInstance
import com.example.shopstar.data.Model.Product
import com.example.shopstar.data.ProductRepository
import com.example.shopstar.ui.theme.ShopStarTheme
import com.example.shopstar.ui_layer.ProductsViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ProductsViewModel> (factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductsViewModel(ProductRepository(ApiInstance.api)) as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopStarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   val context = LocalContext.current
                    val productsLists = viewModel.products.collectAsState().value
                    LaunchedEffect(key1 = viewModel.showError) {
                        viewModel.showError.collectLatest {
                            if(it){
                                Toast.makeText(context, "Failed to Load Data", Toast.LENGTH_SHORT).show()

                            }
                        }

                    }

                    if(productsLists.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }else{
                        LazyColumn (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)){

                            items(productsLists){
                                ProductItem(product = it)
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                        }

                        }
                    }
                }
            }
        }

    @Composable
  fun ProductItem(product: Product) {
      Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
      ){
          AsyncImage(model= product.thumbnail, contentDescription = null , modifier = Modifier
              .fillMaxWidth()
              .height(200.dp), placeholder = painterResource(
              id = R.drawable.ic_launcher_background)
          )
          Spacer(modifier = Modifier.padding(horizontal = 16.dp))

          Text(modifier = Modifier.padding(horizontal = 16.dp),
              text = "${product.title}- Price ${product.price}",
              fontSize = 18.sp, fontWeight = FontWeight.SemiBold
              )

          Spacer(modifier = Modifier.padding(horizontal =6.dp))

          Text(modifier = Modifier.padding(horizontal = 16.dp),
              text = product.description,
              fontSize = 14.sp, fontWeight = FontWeight.Normal
          )

      }
    }
}

