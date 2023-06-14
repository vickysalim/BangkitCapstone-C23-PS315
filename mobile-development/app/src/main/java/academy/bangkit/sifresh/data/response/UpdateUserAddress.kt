package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateUserAddress(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("kecamatan")
	val kecamatan: String,

	@field:SerializedName("kodePos")
	val kodePos: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable
