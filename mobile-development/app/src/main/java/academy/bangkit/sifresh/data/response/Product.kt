package academy.bangkit.sifresh.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
data class Product(
	@field:SerializedName("product")
	val product: List<ProductItem>
) : Parcelable

@Parcelize
@Entity (tableName = "productItem")
data class ProductItem(
	@field:SerializedName("isAvailable")
	val isAvailable: Int,

	@field:SerializedName("sellerId")
	val sellerId: String,

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("productPicUrls")
	val productPicUrls: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("sellerName")
	val sellerName: String,

	@field:SerializedName("description")
	val description: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable