package academy.bangkit.sifresh.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(

    @field:SerializedName("message")
    val message: String
) : Parcelable
