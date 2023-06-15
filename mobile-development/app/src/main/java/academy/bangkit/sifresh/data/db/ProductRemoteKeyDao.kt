package academy.bangkit.sifresh.data.db

import academy.bangkit.sifresh.data.response.ProductRemoteKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ProductRemoteKey>)

    @Query("SELECT * FROM product_remote_key WHERE id = :id")
    suspend fun getRemoteKeyId(id: String): ProductRemoteKey?

    @Query("DELETE FROM product_remote_key")
    suspend fun deleteRemoteKey()
}