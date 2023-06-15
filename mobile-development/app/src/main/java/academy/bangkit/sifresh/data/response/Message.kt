package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Message(

	@field:SerializedName("message")
	val message: String
) : Parcelable
