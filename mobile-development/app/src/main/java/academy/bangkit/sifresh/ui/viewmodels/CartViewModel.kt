package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.Cart
import academy.bangkit.sifresh.data.response.CartItem
import academy.bangkit.sifresh.data.response.OrderHistoryData
import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {
    val userId = MutableLiveData("")

    var cartItem: MutableLiveData<CartItem> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)

    internal fun getUserCartItemPerProduct(productId: String) {
        val client =
            ApiConfig.getApiService().getUserCartItemPerProduct(userId.value.toString(), productId)

        client.enqueue(object : Callback<Cart> {
            override fun onResponse(
                call: Call<Cart>,
                response: Response<Cart>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.data != null) {
                        if(result.data.isNotEmpty()) {
                            cartItem.value = result.data[0]
                            status.value = ResponseCode.SUCCESS
                        } else {
                            status.value = ResponseCode.NOT_FOUND
                        }
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                    status.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}