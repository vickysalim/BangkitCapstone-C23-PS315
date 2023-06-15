package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.db.ProductDatabase
import academy.bangkit.sifresh.data.repository.ProductRepository
import academy.bangkit.sifresh.data.retrofit.ApiService
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductViewModelFactory(
    val context: Context,
    private val apiService: ApiService
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductPagerViewModel::class.java)) {
            val database = ProductDatabase.getDatabase(context)
            return ProductPagerViewModel(ProductRepository(database, apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}