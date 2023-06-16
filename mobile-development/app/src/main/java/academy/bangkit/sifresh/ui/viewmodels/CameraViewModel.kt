package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.Freshness
import academy.bangkit.sifresh.data.response.FreshnessData
import academy.bangkit.sifresh.data.response.Prediction
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel : ViewModel() {
    val userId = MutableLiveData("")
    var data: MutableLiveData<FreshnessData> = MutableLiveData()
    var prediction: MutableLiveData<Prediction> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)

    internal fun uploadPhoto(id: RequestBody, image: MultipartBody.Part) {
        status.value = ResponseCode.NOTHING
        val client = ApiConfig.getApiService().predictVegetables(id, image)

        client.enqueue(object : Callback<Freshness> {
            override fun onResponse(call: Call<Freshness>, response: Response<Freshness>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        data.value = result.data
                        prediction.value = result.prediction
                        status.value = ResponseCode.SUCCESS
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                } else {
                    status.value = ResponseCode.FORBIDDEN
                }
            }

            override fun onFailure(call: Call<Freshness>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}