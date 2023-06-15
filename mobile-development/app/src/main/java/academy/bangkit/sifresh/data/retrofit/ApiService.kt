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
    ): Call<GetUserData>

    @GET("user/get/{id}")
    fun getUserDataById(
        @Path("id") id: String
    ): Call<GetUserData>

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

    @FormUrlEncoded
    @POST("user/post/update")
    fun updateUser(
        @Header("Authorization") token: String,
        @Field("id") id: String,
        @Field("fullName") fullName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
    ): Call<UpdateUser>

    @FormUrlEncoded
    @POST("user/post/update-address")
    fun updateUserAddress(
        @Header("Authorization") token: String,
        @Field("id") id: String,
        @Field("address") address: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("kecamatan") kecamatan: String,
        @Field("kodePos") kodePos: String,
    ): Call<UpdateUserAddress>

    @Multipart
    @POST("user/post/update-profile-pic")
    fun updateProfilePic(
        @Header("Authorization") token: String,
        @Part("id") id: RequestBody,
        @Part profilePic: MultipartBody.Part,
    ): Call<UpdateUser>

    @GET("order/item/get/{id}")
    fun getOrderList(
        @Path("id") id: String
    ): Call<OrderHistory>

    @GET("product/get")
    suspend fun product(
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : List<ProductItem>

    @GET("product/get/name/{name}")
    fun searchProduct(
        @Path("name") name: String
    ) : Call<List<ProductItem>>

    @GET("cart/get/{id}/{productId}")
    fun getUserCartItemPerProduct(
        @Path("id") id: String,
        @Path("productId") productId: String
    ): Call<Cart>

}