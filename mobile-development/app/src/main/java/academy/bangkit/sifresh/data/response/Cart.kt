package academy.bangkit.sifresh.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(

    @field:SerializedName("data")
    val data: List<CartItem>,

    @field:SerializedName("message")
    val message: String
) : Parcelable

@Parcelize
data class CartItem(

    @field:SerializedName("amount")
    val amount: Int,

    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("status")
    val status: String
) : Parcelable
