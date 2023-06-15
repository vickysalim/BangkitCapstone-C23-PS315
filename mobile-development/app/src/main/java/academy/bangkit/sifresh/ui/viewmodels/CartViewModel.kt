package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.*
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {
    val userToken = MutableLiveData("")
    val userId = MutableLiveData("")

    var cartList: MutableLiveData<List<CartSellerItem>> = MutableLiveData()

    var cartItem: MutableLiveData<CartItem> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)
    var updateStatus = MutableLiveData(ResponseCode.NOTHING)

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
                        if (result.data.isNotEmpty()) {
                            status.value = ResponseCode.SUCCESS
                            cartItem.value = result.data[0]
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

    internal fun updateCartItem(productId: String, amount: Int) {
        updateStatus.value = ResponseCode.LOADING
        val client =
            ApiConfig.getApiService().updateCartItem(userId.value.toString(), productId, amount)

        client.enqueue(object : Callback<Message> {
            override fun onResponse(
                call: Call<Message>,
                response: Response<Message>
            ) {
                if (response.isSuccessful) {
                    updateStatus.value = ResponseCode.SUCCESS
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                    updateStatus.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun deleteCartItem() {
        val id = cartItem.value?.id
        val token = "Bearer ${userToken.value}"

        val client = ApiConfig.getApiService().deleteCart(token, id.toString(), userId.value.toString())

        client.enqueue(object : Callback<Message> {
            override fun onResponse(
                call: Call<Message>,
                response: Response<Message>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.message == "Cart item deleted") {
                        status.value = ResponseCode.SUCCESS
                        getUserCartItemPerProduct(cartItem.value?.productId.toString())
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                    status.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun addCartItem(sellerId: String, productId: String) {
        val token = "Bearer ${userToken.value}"
        val client = ApiConfig.getApiService()
            .addCartItem(token, userId.value.toString(), sellerId, productId)

        client.enqueue(object : Callback<Message> {
            override fun onResponse(
                call: Call<Message>,
                response: Response<Message>
            ) {
                if (response.isSuccessful) {
                    status.value = ResponseCode.SUCCESS
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                    status.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun getCartData(userId: String) {
        val client = ApiConfig.getApiService().getAllCart(userId)

        client.enqueue(object : Callback<CartSeller> {
            override fun onResponse(
                call: Call<CartSeller>,
                response: Response<CartSeller>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.e("Test", "onResponse: ${body?.data}")
                    if(body?.data != null) {
                        cartList.value = body.data
                    }
                    status.value = ResponseCode.SUCCESS
                } else {
                    Log.e("MainActivity", "onFailure1: ${response.message()}")
                    status.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<CartSeller>, t: Throwable) {
                Log.e("MainActivity", "onFailure2: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}