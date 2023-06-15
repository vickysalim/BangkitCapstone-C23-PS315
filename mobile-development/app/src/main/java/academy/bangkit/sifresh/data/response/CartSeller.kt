package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CartSeller(

	@field:SerializedName("data")
	val data: List<CartSellerItem>,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class CartSellerItem(

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("totalPrice")
	val totalPrice: String,

	@field:SerializedName("products")
	val products: List<CartItems>
) : Parcelable

@Parcelize
data class CartItems(

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("productId")
	val productId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("productName")
	val productName: String,

	@field:SerializedName("productPrice")
	val productPrice: Int,

	@field:SerializedName("status")
	val status: String
) : Parcelable
