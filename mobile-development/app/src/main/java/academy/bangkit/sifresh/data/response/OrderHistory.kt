package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderHistory(

	@field:SerializedName("data")
	val data: List<OrderHistoryData>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class OrderHistoryData(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("orderDate")
	val orderDate: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable
