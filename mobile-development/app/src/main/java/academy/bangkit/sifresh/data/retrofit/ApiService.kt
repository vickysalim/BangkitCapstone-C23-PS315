package academy.bangkit.sifresh.data.retrofit

import academy.bangkit.sifresh.data.response.Login
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("user/post/authenticate")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>
}