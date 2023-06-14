package academy.bangkit.sifresh.data.db

import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.data.response.ProductRemoteKey
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductItem::class, ProductRemoteKey::class],
    version = 4,
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productRemoteKeyDao(): ProductRemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}