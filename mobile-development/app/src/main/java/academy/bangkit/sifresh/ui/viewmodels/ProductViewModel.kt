package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _product = MutableLiveData<List<ProductItem>>()
    val product: LiveData<List<ProductItem>> = _product

    internal fun findProduct(query: String) {
        val client = ApiConfig.getApiService().searchProduct(query)
        client.enqueue(object : Callback<List<ProductItem>> {
            override fun onResponse(
                call: Call<List<ProductItem>>,
                response: Response<List<ProductItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _product.value = response.body()
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductItem>>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}