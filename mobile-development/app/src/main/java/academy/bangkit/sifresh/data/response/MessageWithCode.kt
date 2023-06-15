package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MessageWithCode(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("message")
	val message: String
) : Parcelable
