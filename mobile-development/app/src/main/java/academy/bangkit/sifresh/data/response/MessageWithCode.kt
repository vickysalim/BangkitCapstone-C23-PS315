package academy.bangkit.sifresh.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageWithCode(

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("message")
    val message: String
) : Parcelable
