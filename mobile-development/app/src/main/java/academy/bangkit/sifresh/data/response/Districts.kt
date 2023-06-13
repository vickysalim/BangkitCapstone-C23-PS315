package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Districts(

	@field:SerializedName("districts")
	val districts: List<District>
) : Parcelable

@Parcelize
data class District(

	@field:SerializedName("regency_id")
	val regencyId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable
