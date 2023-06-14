package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Provinces(
	val provinces: List<Province>
) : Parcelable

@Parcelize
data class Province(
	val name: String,
	val id: String
) : Parcelable
