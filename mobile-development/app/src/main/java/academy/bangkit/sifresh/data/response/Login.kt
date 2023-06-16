package academy.bangkit.sifresh.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(

    @field:SerializedName("cred")
    val cred: Cred,

    @field:SerializedName("jwt")
    val jwt: String
) : Parcelable

@Parcelize
data class Cred(

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("profilePicUrl")
    val profilePicUrl: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("isSeller")
    val isSeller: Int,

    @field:SerializedName("fullName")
    val fullName: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
) : Parcelable
