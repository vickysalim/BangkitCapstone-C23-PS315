package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.Message
import academy.bangkit.sifresh.data.response.MessageWithCode
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
    val userToken = MutableLiveData("")

    val sellerId = MutableLiveData("")

    var orderList: MutableLiveData<List<OrderListItem>> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)
    var updateOrderStatus = MutableLiveData(ResponseCode.NOTHING)
    var insertStatus = MutableLiveData(ResponseCode.NOTHING)

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
        val token = "Bearer ${userToken.value}"
        val client = ApiConfig.getApiService().confirmOrder(token, id)

        client.enqueue(object : Callback<MessageWithCode> {
            override fun onResponse(
                call: Call<MessageWithCode>,
                response: Response<MessageWithCode>
            ) {
                if (response.isSuccessful) {
                    insertStatus.value = ResponseCode.SUCCESS
                } else {
                    insertStatus.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(
                call: Call<MessageWithCode>, t: Throwable
            ) {
                insertStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun updateOrderStatus(id: String, sellerId: String, orderStatus: String) {
        val client = ApiConfig.getApiService().setOrderStatus(id, sellerId, orderStatus)

        client.enqueue(object : Callback<Message> {
            override fun onResponse(
                call: Call<Message>,
                response: Response<Message>
            ) {
                if (response.isSuccessful) {
                    updateOrderStatus.value = ResponseCode.SUCCESS
                } else {
                    updateOrderStatus.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(
                call: Call<Message>, t: Throwable
            ) {
                updateOrderStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}