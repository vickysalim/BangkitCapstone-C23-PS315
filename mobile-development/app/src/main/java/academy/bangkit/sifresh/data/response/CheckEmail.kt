package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CheckEmail(

	@field:SerializedName("user")
	val user: CheckEmailResult? = null,
) : Parcelable

@Parcelize
data class CheckEmailResult(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("profilePicUrl")
	val profilePicUrl: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("isSeller")
	val isSeller: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("kodePos")
	val kodePos: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
