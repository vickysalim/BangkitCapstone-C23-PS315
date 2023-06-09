package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Register(

	@field:SerializedName("code")
	val code: String?,

	@field:SerializedName("message")
	val message: String?,

	@field:SerializedName("password")
	val password: String?,

	@field:SerializedName("profilePicUrl")
	val profilePicUrl: String?,

	@field:SerializedName("phone")
	val phone: String?,

	@field:SerializedName("isSeller")
	val isSeller: Int?,

	@field:SerializedName("fullName")
	val fullName: String?,

	@field:SerializedName("id")
	val id: String?,

	@field:SerializedName("email")
	val email: String?

) : Parcelable
