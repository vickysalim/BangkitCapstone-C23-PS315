package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.OrderHistory
import academy.bangkit.sifresh.data.response.OrderHistoryData
import academy.bangkit.sifresh.data.response.Provinces
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    val userId = MutableLiveData("")
    var orderList: MutableLiveData<List<OrderHistoryData>> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)

    internal fun getOrderList(id: String) {
        val client = ApiConfig.getApiService().getOrderList(id)

        client.enqueue(object : Callback<OrderHistory> {
            override fun onResponse(
                call: Call<OrderHistory>,
                response: Response<OrderHistory>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.data != null) {
                        orderList.value = result.data!!
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<OrderHistory>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}