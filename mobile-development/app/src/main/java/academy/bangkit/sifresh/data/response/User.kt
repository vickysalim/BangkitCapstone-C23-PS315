package academy.bangkit.sifresh.data.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class User(
    @field:SerializedName("id")
    val id: UUID,

    @field:SerializedName("fullName")
    val fullName: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("isSeller")
    val isSeller: Boolean,

    @field:SerializedName("profilePicUrl")
    val profilePicUrl: String,

    @field:SerializedName("address")
    val province: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("kodePos")
    val kodePos: String
)
