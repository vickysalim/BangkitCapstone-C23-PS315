package academy.bangkit.sifresh.ui.viewmodels

import academy.bangkit.sifresh.data.repository.ProductRepository
import academy.bangkit.sifresh.data.response.ProductItem
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class ProductPagerViewModel(repository: ProductRepository) : ViewModel() {
    val product: LiveData<PagingData<ProductItem>> = repository.getProduct().cachedIn(viewModelScope)
    val productFruit: LiveData<PagingData<ProductItem>> = repository.getProductFruit().cachedIn(viewModelScope)
    val productVegetable: LiveData<PagingData<ProductItem>> = repository.getProductVegetable().cachedIn(viewModelScope)
}