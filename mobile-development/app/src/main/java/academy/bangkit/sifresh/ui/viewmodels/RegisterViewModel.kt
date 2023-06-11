package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.User
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RegisterViewModel : ViewModel() {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null

    var province: String? = null
    var city: String? = null
    var district: String? = null
    var postalCode: String? = null
    var address: String? = null

    // check if email is valid from retrofit
    internal fun isEmailValid() {
        val client = ApiConfig.getApiService().checkEmail(email!!)

        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        Log.d("RegisterViewModel", "onResponse: ${result.email}")
                    }
                }
            }

            override fun onFailure(
                call: Call<User>, t: Throwable
            ) {
                Log.e("RegisterViewModel", "onFailed: ${t.message}")
            }
        })
    }

    // register user from retrofit
    internal fun registerUser() {
        val client = ApiConfig.getApiService()
            .register(
                toRequestBody(name),
                toRequestBody(email),
                toRequestBody(password),
                toRequestBody(phoneNumber),
                toRequestBody(0.toString()),
                asRequestBody(File(""))
            )
    }

    private fun toRequestBody(value: String?): RequestBody =
        value!!.toRequestBody("text/plain".toMediaType())

    private fun asRequestBody(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "photo",
            file.name,
            file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
    }
}