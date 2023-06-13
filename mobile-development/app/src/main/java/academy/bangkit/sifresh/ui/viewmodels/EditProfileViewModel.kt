package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.response.*
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
import java.io.File

class EditProfileViewModel : ViewModel() {
    val userToken = MutableLiveData("")
    val userId = MutableLiveData("")
    val user: MutableLiveData<GetUserDataResult?> = MutableLiveData()

    var provincesList: MutableLiveData<List<Province>> = MutableLiveData()
    var citiesList: MutableLiveData<List<City>> = MutableLiveData()
    var districtsList: MutableLiveData<List<District>> = MutableLiveData()

    var locationStatus = MutableLiveData(ResponseCode.NOTHING)
    var updateDataStatus = MutableLiveData(ResponseCode.NOTHING)
    var userDataStatus = MutableLiveData(ResponseCode.NOTHING)

    var file: MutableLiveData<File> = MutableLiveData()

    internal fun getUserData() {
        userDataStatus.value = ResponseCode.LOADING
        val client = ApiConfig.getApiService().getUserDataById(userId.value.toString())

        client.enqueue(object : Callback<GetUserData> {
            override fun onResponse(
                call: Call<GetUserData>,
                response: Response<GetUserData>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        if (result.user?.id.toString() == "null") {
                            userDataStatus.value = ResponseCode.NOT_FOUND
                            Log.e("ID 1: ", result.user?.id.toString())
                        } else {
                            userDataStatus.value = ResponseCode.SUCCESS
                            user.value = result.user
                            Log.e("ID 2: ", result.user?.id.toString())
                        }
                    } else {
                        userDataStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<GetUserData>, t: Throwable
            ) {
                userDataStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun saveUserData(
        fullName: String,
        province: String,
        city: String,
        district: String,
        address: String,
        postalCode: String
    ) {
        val auth = "Bearer ${userToken.value.toString()}"

        val clientUpdateUser = ApiConfig.getApiService().updateUser(
            auth,
            userId.value.toString(),
            fullName,
            user.value?.email.toString(),
            user.value?.phone.toString()
        )

        val clientUpdateLocation = ApiConfig.getApiService().updateUserAddress(
            auth,
            userId.value.toString(),
            address,
            province,
            city,
            district,
            postalCode
        )

        clientUpdateUser.enqueue(object : Callback<UpdateUser> {
            override fun onResponse(
                call: Call<UpdateUser>,
                response: Response<UpdateUser>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        clientUpdateLocation.enqueue(object : Callback<UpdateUserAddress> {
                            override fun onResponse(
                                call: Call<UpdateUserAddress>,
                                response: Response<UpdateUserAddress>
                            ) {
                                if (response.isSuccessful) {
                                    val resultAddAddress = response.body()
                                    if (resultAddAddress != null) {
                                        updateDataStatus.value = ResponseCode.SUCCESS
                                    } else {
                                        updateDataStatus.value = ResponseCode.FORBIDDEN
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<UpdateUserAddress>, t: Throwable
                            ) {
                                updateDataStatus.value = ResponseCode.SERVER_TIMEOUT
                            }
                        })
                    } else {
                        updateDataStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<UpdateUser>, t: Throwable
            ) {
                updateDataStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }

    internal fun uploadPhoto(id: RequestBody, photo: MultipartBody.Part) {
        val auth = "Bearer ${userToken.value.toString()}"

        val client = ApiConfig.getApiService().updateProfilePic(
            auth,
            id,
            photo
        )

        Log.e("Get Input Value", "$id $photo $auth")

        client.enqueue(object : Callback<UpdateUser> {
            override fun onResponse(
                call: Call<UpdateUser>,
                response: Response<UpdateUser>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
//                    if (result == null) {
//                        updateDataStatus.value = ResponseCode.FORBIDDEN
//                    }
                    Log.e("Upload Photo: ", result.toString())
                }
            }

            override fun onFailure(
                call: Call<UpdateUser>, t: Throwable
            ) {
                updateDataStatus.value = ResponseCode.SERVER_TIMEOUT
                Log.e("Upload Photo ERROR: ", t.toString())
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
                        locationStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Provinces>, t: Throwable
            ) {
                locationStatus.value = ResponseCode.SERVER_TIMEOUT
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
                        locationStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Cities>, t: Throwable
            ) {
                locationStatus.value = ResponseCode.SERVER_TIMEOUT
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
                        locationStatus.value = ResponseCode.FORBIDDEN
                    }
                }
            }

            override fun onFailure(
                call: Call<Districts>, t: Throwable
            ) {
                locationStatus.value = ResponseCode.SERVER_TIMEOUT
            }
        })
    }
}