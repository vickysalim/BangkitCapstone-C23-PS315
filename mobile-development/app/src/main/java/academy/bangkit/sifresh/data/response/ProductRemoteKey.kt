package academy.bangkit.sifresh.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_remote_key")
data class ProductRemoteKey(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
