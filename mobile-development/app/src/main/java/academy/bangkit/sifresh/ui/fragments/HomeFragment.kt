package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.local.SettingPreferences
import academy.bangkit.sifresh.data.local.dataStore
import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.databinding.FragmentHomeBinding
import academy.bangkit.sifresh.ui.activities.MainActivity
import academy.bangkit.sifresh.ui.adapter.ListProductAdapter
import academy.bangkit.sifresh.ui.viewmodels.ProductViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModel
import academy.bangkit.sifresh.ui.viewmodels.SettingViewModelFactory
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val productAdapter = ListProductAdapter()
    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://gust-production.s3.amazonaws.com/uploads/startup/panoramic_image/1007246/fruits-and-Vegetables-banner.jpg"))
        imageList.add(SlideModel("https://thumbs.dreamstime.com/b/background-food-fruits-vegetables-collection-fruit-vegetable-healthy-eating-diet-apples-oranges-banner-tomatoes-backgrounds-190445608.jpg"))
        imageList.add(SlideModel("https://warwick.ac.uk/fac/soc/economics/research/centres/cage/news/08-07-16-eating_more_fruit_and_vegetables_can_make_you_happier/banner.jpg"))

        binding.imageSliderBanner.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSliderBanner.setSlideAnimation(AnimationTypes.ZOOM_OUT)

        setUpProductList()

        productViewModel.product.observe(viewLifecycleOwner) { product ->
            setProductSearchResult(product)
        }
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchBar.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                productViewModel.findProduct(query)
                binding.searchBar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setUpProductList() {
        val preferences = SettingPreferences.getInstance(requireContext().dataStore)
        val preferencesViewModel =
            ViewModelProvider(
                this@HomeFragment,
                SettingViewModelFactory(preferences)
            )[SettingViewModel::class.java]
        preferencesViewModel.getUserPreferences(SettingPreferences.Companion.UserPreferences.UserToken.name)
            .observe(viewLifecycleOwner) { token ->
                (activity as MainActivity).tempToken = "Bearer $token"
                val productViewModel = (activity as MainActivity).getProductViewModel()
                productViewModel.product.observe(viewLifecycleOwner) {
                    productAdapter.submitData(lifecycle, it)
                }

                binding.rbCategoryAll.isChecked = true
                binding.rvMarketplaceItem.apply {
                    binding.rbCategoryAll.setTextColor(resources.getColor(R.color.white))
                    binding.rbCategoryFruit.setTextColor(resources.getColor(R.color.light_green))
                    binding.rbCategoryVegetable.setTextColor(resources.getColor(R.color.light_green))
                    setHasFixedSize(false)
                    layoutManager = GridLayoutManager(context, 2)
                    isNestedScrollingEnabled = false
                    adapter = productAdapter
                }
                binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.rb_category_all -> {
                            binding.rbCategoryAll.setTextColor(resources.getColor(R.color.white))
                            binding.rbCategoryFruit.setTextColor(resources.getColor(R.color.light_green))
                            binding.rbCategoryVegetable.setTextColor(resources.getColor(R.color.light_green))
                            productViewModel.product.observe(viewLifecycleOwner) {
                                productAdapter.submitData(lifecycle, it)
                            }
                            binding.rvMarketplaceItem.adapter = productAdapter
                        }
                        R.id.rb_category_fruit -> {
                            binding.rbCategoryAll.setTextColor(resources.getColor(R.color.light_green))
                            binding.rbCategoryFruit.setTextColor(resources.getColor(R.color.white))
                            binding.rbCategoryVegetable.setTextColor(resources.getColor(R.color.light_green))
                            productViewModel.productFruit.observe(viewLifecycleOwner) {
                                productAdapter.submitData(lifecycle, it)
                            }
                            binding.rvMarketplaceItem.adapter = productAdapter
                            binding.searchBar.setQuery("", false)
                        }
                        R.id.rb_category_vegetable -> {
                            binding.rbCategoryAll.setTextColor(resources.getColor(R.color.light_green))
                            binding.rbCategoryFruit.setTextColor(resources.getColor(R.color.light_green))
                            binding.rbCategoryVegetable.setTextColor(resources.getColor(R.color.white))
                            productViewModel.productVegetable.observe(viewLifecycleOwner) {
                                productAdapter.submitData(lifecycle, it)
                            }
                            binding.rvMarketplaceItem.adapter = productAdapter
                            binding.searchBar.setQuery("", false)
                        }
                    }
                }

            }
    }

    private fun setProductSearchResult(product: List<ProductItem>) {
        val productList = ArrayList<ProductItem>()
        for (i in product) {
            productList.clear()
            productList.addAll(product)
        }
        val paging: PagingData<ProductItem> = PagingData.from(productList)
        productAdapter.submitData(lifecycle, paging)
        binding.rvMarketplaceItem.adapter = productAdapter
    }
}