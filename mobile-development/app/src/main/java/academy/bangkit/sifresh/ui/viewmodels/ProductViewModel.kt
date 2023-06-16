package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProductViewModel : ViewModel() {
    val userToken = MutableLiveData("")
    val sellerId = MutableLiveData("")
    val sellerName = MutableLiveData("")
    val productType = MutableLiveData("")
    val addProduct = MutableLiveData(ResponseCode.NOTHING)
    var file: MutableLiveData<File> = MutableLiveData()
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

    fun addProduct(
        name: String,
        type: String,
        price: Double,
        isAvailable: Boolean = true,
        description: String,
        // image: MultipartBody.Part,
        publishedAt: String
    ) {
        val auth = "Bearer ${userToken.value.toString()}"
        val productSellerId = sellerId.value.toString() //.toRequestBody("text/plain".toMediaType())
        val productName = name //.toRequestBody("text/plain".toMediaType())
        val productSellerName = sellerName.value.toString() //.toRequestBody("text/plain".toMediaType())
        val productDescription = description //.toRequestBody("text/plain".toMediaType())
        val productType = type //.toRequestBody("text/plain".toMediaType())
        val productPrice = price.toInt() //.toRequestBody("text/plain".toMediaType())
        val productPublishedAt = publishedAt //.toRequestBody("text/plain".toMediaType())
        val productIsAvailable = isAvailable //.toRequestBody("text/plain".toMediaType())
        // val reqImageFile = Helper.reduceFileImage(image).asRequestBody("image/jpeg".toMediaTypeOrNull())
        // val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", image.name, reqImageFile)

        val client = ApiConfig.getApiService().addProduct(
            auth,
            productSellerId,
            productName,
            productSellerName,
            productType,
            productPrice,
            productIsAvailable,
            productDescription,
            // image,
            productPublishedAt
        )
        client.enqueue(object : Callback<ProductItem> {
            override fun onResponse(call: Call<ProductItem>, response: Response<ProductItem>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        addProduct.value = ResponseCode.SUCCESS
                    } else {
                        addProduct.value = ResponseCode.FORBIDDEN
                    }
                } else {
                    Log.d("ProductViewModel", "Failed Add Product")
                }
            }

            override fun onFailure(call: Call<ProductItem>, t: Throwable) {
                addProduct.value = ResponseCode.SERVER_TIMEOUT
                Log.e("ProductViewModel", "onFailed: ${t.message}")
            }
        })
    }
}