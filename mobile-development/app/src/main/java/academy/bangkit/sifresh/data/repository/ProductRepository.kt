package academy.bangkit.sifresh.data.repository

import academy.bangkit.sifresh.data.ProductRemoteMediator
import academy.bangkit.sifresh.data.db.ProductDatabase
import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.data.retrofit.ApiService
import androidx.lifecycle.LiveData
import androidx.paging.*
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDatabase: ProductDatabase,
    private val apiService: ApiService
) {
    fun getProduct(): LiveData<PagingData<ProductItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ProductRemoteMediator(productDatabase, apiService),
            pagingSourceFactory = {
                productDatabase.productDao().getAllProduct()
            }
        ).liveData
    }

    fun getProductFruit(): LiveData<PagingData<ProductItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ProductRemoteMediator(productDatabase, apiService),
            pagingSourceFactory = {
                productDatabase.productDao().getProductFruit()
            }
        ).liveData
    }

    fun getProductVegetable(): LiveData<PagingData<ProductItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ProductRemoteMediator(productDatabase, apiService),
            pagingSourceFactory = {
                productDatabase.productDao().getProductVegetable()
            }
        ).liveData
    }
}