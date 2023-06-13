package academy.bangkit.sifresh.data.retrofit

import academy.bangkit.sifresh.data.response.*
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("user/post/authenticate")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>

    @FormUrlEncoded
    @POST("user/post/add")
    fun register(
        @Field("fullName") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("isSeller") isSeller: Boolean,
        //@Part profilePic: MultipartBody.Part,
    ): Call<Register>

    @FormUrlEncoded
    @POST("user/post/add-address")
    fun addAddress(
        @Field("id") id: String,
        @Field("address") address: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("kecamatan") kecamatan: String,
        @Field("kodePos") kodePos: String,
    ): Call<AddAddress>

    @GET("user/get/email/{email}")
    fun checkEmail(
        @Path("email") email: String
    ): Call<CheckEmail>

    @GET("location/get")
    fun getProvince(): Call<Provinces>

    @GET("location/get/{provinceId}")
    fun getCities(
        @Path("provinceId") provinceId: String
    ): Call<Cities>

    @GET("location/get/{provinceId}/{cityId}")
    fun getDistricts(
        @Path("provinceId") provinceId: String,
        @Path("cityId") cityId: String
    ): Call<Districts>
}