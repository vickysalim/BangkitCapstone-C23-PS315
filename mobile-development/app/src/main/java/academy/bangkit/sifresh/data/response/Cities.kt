package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Cities(

	@field:SerializedName("cities")
	val cities: List<City>
) : Parcelable

@Parcelize
data class City(

	@field:SerializedName("province_id")
	val provinceId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String
) : Parcelable
