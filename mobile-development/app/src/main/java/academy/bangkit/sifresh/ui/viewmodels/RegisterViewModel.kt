package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.*
import academy.bangkit.sifresh.data.retrofit.ApiConfig
import academy.bangkit.sifresh.utils.ResponseCode
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(), LifecycleObserver {
    var name = MutableLiveData("")
    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var phoneNumber = MutableLiveData("")

    var province = MutableLiveData("")
    var city = MutableLiveData("")
    var district = MutableLiveData("")
    var postalCode = MutableLiveData("")
    var address = MutableLiveData("")

    var provincesList: MutableLiveData<List<Province>> = MutableLiveData()
    var citiesList: MutableLiveData<List<City>> = MutableLiveData()
    var districtsList: MutableLiveData<List<District>> = MutableLiveData()

    var status = MutableLiveData(ResponseCode.NOTHING)
    var emailValidStatus = MutableLiveData(ResponseCode.NOTHING)

    internal fun isEmailValid(email: String) {
        emailValidStatus.value = ResponseCode.LOADING
        val client = ApiConfig.getApiService().checkEmail(email)

        client.enqueue(object : Callback<GetUserData> {
            override fun onResponse(
                call: Call<GetUserData>,
                response: Response<GetUserData>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        if (result.user?.email.toString() == "null") {
                            emailValidStatus.value = ResponseCode.SUCCESS
                            Log.e("EMAIL 1: ", result.user?.email.toString())
                        } else {
                            emailValidStatus.value = ResponseCode.NOT_FOUND
                            Log.e("EMAIL 2: ", result.user?.email.toString())
                        }
                    } else {
                        emailValidStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<GetUserData>, t: Throwable
            ) {
                emailValidStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun registerUser() {
        val client = ApiConfig.getApiService()
            .register(
                name.value.toString(),
                email.value.toString(),
                password.value.toString(),
                "+62${phoneNumber.value.toString()}",
                false,
            )

        client.enqueue(object : Callback<Register> {
            override fun onResponse(
                call: Call<Register>,
                response: Response<Register>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.code == null) {
                        registerUserAddress(result?.id.toString())
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Register>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun registerUserAddress(id: String) {
        val client = ApiConfig.getApiService()
            .addAddress(
                id,
                address.value.toString(),
                province.value.toString(),
                city.value.toString(),
                district.value.toString(),
                postalCode.value.toString(),
            )

        client.enqueue(object : Callback<AddAddress> {
            override fun onResponse(
                call: Call<AddAddress>,
                response: Response<AddAddress>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        status.value = ResponseCode.SUCCESS
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<AddAddress>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun getProvince() {
        val client = ApiConfig.getApiService().getProvince()

        client.enqueue(object : Callback<Provinces> {
            override fun onResponse(
                call: Call<Provinces>,
                response: Response<Provinces>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        provincesList.value = result.provinces
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Provinces>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun getCity(id: String) {
        val client = ApiConfig.getApiService().getCities(id)

        client.enqueue(object : Callback<Cities> {
            override fun onResponse(
                call: Call<Cities>,
                response: Response<Cities>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        citiesList.value = result.cities
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Cities>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun getDistrict(provinceId: String, cityId: String) {
        val client = ApiConfig.getApiService().getDistricts(provinceId, cityId)

        client.enqueue(object : Callback<Districts> {
            override fun onResponse(
                call: Call<Districts>,
                response: Response<Districts>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        districtsList.value = result.districts
                    } else {
                        status.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Districts>, t: Throwable
            ) {
                status.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}