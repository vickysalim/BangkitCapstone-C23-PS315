package academy.bangkit.sifresh.data.db

import academy.bangkit.sifresh.data.response.ProductItem
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductItem>)

    @Query("SELECT * FROM productItem ORDER BY publishedAt")
    fun getAllProduct(): PagingSource<Int, ProductItem>

    @Query("SELECT * FROM productItem WHERE type=\"fruit\"")
    fun getProductFruit(): PagingSource<Int, ProductItem>

    @Query("SELECT * FROM productItem WHERE type=\"vegetable\"")
    fun getProductVegetable(): PagingSource<Int, ProductItem>

    @Query("SELECT * FROM productItem WHERE name LIKE '%' || :query || '%' ORDER BY publishedAt")
    fun searchProduct(query: String): PagingSource<Int, ProductItem>

    @Query("DELETE FROM productItem")
    suspend fun deleteAll()
}