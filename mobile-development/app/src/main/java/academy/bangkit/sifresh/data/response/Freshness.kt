package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Freshness(

	@field:SerializedName("data")
	val data: FreshnessData,

	@field:SerializedName("prediction")
	val prediction: Prediction
) : Parcelable

@Parcelize
data class FreshnessData(

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

@Parcelize
data class Prediction(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("predictions")
	val predictions: Predictions
) : Parcelable

@Parcelize
data class Predictions(

	@field:SerializedName("confidence")
	val confidence: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("class")
	val status: String
) : Parcelable
