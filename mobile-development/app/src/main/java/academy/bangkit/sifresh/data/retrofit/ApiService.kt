package academy.bangkit.sifresh.data.retrofit

import academy.bangkit.sifresh.data.response.Login
import retrofit2.Call
import retrofit2.http.*
import academy.bangkit.sifresh.data.response.AddAddress
import academy.bangkit.sifresh.data.response.Register
import academy.bangkit.sifresh.data.response.User
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

    @Multipart
    @POST("user/post/add")
    fun register(
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("isSeller") isSeller: RequestBody,
        @Part("profilePicUrl") profilePicUrl: MultipartBody.Part,
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
    ): Call<User>
}