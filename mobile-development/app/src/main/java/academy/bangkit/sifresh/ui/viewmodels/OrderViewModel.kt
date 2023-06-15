package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.OrderList
import academy.bangkit.sifresh.data.response.OrderListItem
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel() {
    val userId = MutableLiveData("")
    val sellerId = MutableLiveData("")

    var orderList: MutableLiveData<List<OrderListItem>> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)

    internal fun getOrderList(id: String, sellerId: String) {
        val client = ApiConfig.getApiService().getCartBySeller(id, sellerId)

        client.enqueue(object : Callback<OrderList> {
            override fun onResponse(
                call: Call<OrderList>,
                response: Response<OrderList>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.data != null) {
                        orderList.value = result.data
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<OrderList>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun insertOrderHistory(id: String) {
        val client = ApiConfig.getApiService().getCartBySeller()

        client.enqueue(object : Callback<OrderList> {
            override fun onResponse(
                call: Call<OrderList>,
                response: Response<OrderList>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.data != null) {
                        orderList.value = result.data
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<OrderList>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}