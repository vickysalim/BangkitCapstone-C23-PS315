package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.Login
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val tempEmail = MutableLiveData("")

    var loginResult = MutableLiveData<Login>()

    fun login(email: String, password: String) {
        tempEmail.postValue(email)
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        loginResult.postValue(response.body())
                        Log.d("LoginViewModel", "onResponse: $result")
                    }
                } else {
                    Log.d("LoginViewModel", "Login Failed")
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.e("LoginViewModel", "onFailed: ${t.message}")
            }
        })
    }
}