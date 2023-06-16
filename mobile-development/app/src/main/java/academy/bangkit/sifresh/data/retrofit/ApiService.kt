package academy.bangkit.sifresh.data.retrofit

import academy.bangkit.sifresh.data.response.*
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    @FormUrlEncoded
    @POST("product/post")
    fun addProduct(
        @Header("Authorization") token: String,
        @Field("sellerId") sellerId: String,
        @Field("name") name: String,
        @Field("sellerName") sellerName: String,
        @Field("type") type: String,
        @Field("price") price: Int,
        @Field("isAvailable") isAvailable: Boolean,
        @Field("description") description: String,
        // @Part productPics: MultipartBody.Part,
        @Field("publishedAt") publishedAt: String,
    ) : Call<ProductItem>

    @GET("cart/get/{id}/{productId}")
    fun getUserCartItemPerProduct(
        @Path("id") id: String,
        @Path("productId") productId: String
    ): Call<Cart>

    @FormUrlEncoded
    @POST("cart/post/update")
    fun updateCartItem(
        @Field("userId") userId: String,
        @Field("productId") productId: String,
        @Field("amount") amount: Int,
    ): Call<Message>

    @FormUrlEncoded
    @POST("cart/del")
    fun deleteCart(
        @Header("Authorization") token: String,
        @Field("id") id: String,
        @Field("userId") userId: String,
    ): Call<Message>

    @FormUrlEncoded
    @POST("cart/post")
    fun addCartItem(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
        @Field("sellerId") sellerId: String,
        @Field("productId") productId: String,
        @Field("amount") amount: Int = 1,
    ): Call<Message>

    @GET("cart/get/seller/{id}")
    fun getAllCart(
        @Path("id") id: String
    ): Call<CartSeller>

    @GET("cart/get/seller/{id}/{sellerId}")
    fun getCartBySeller(
        @Path("id") id: String,
        @Path("sellerId") sellerId: String
    ): Call<OrderList>

    @FormUrlEncoded
    @POST("history/post")
    fun confirmOrder(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
    ): Call<MessageWithCode>

    @FormUrlEncoded
    @POST("cart/post/update-status")
    fun setOrderStatus(
        @Field("userId") userId: String,
        @Field("sellerId") sellerId: String,
        @Field("status") status: String,
    ): Call<Message>
}