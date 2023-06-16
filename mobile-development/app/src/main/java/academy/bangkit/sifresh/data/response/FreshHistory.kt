package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FreshHistory(

	@field:SerializedName("data")
	val data: List<FreshHistoryData>,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class FreshHistoryData(

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("pictureUrl")
	val pictureUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("nutritionDesc")
	val nutritionDesc: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("isFresh")
	val isFresh: Int
) : Parcelable
