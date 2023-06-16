package academy.bangkit.sifresh.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
