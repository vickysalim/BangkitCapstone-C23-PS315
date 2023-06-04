package academy.bangkit.sifresh.ui.fragments

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.ProductDataDummy
import academy.bangkit.sifresh.data.response.Product
import academy.bangkit.sifresh.databinding.FragmentHomeBinding
import academy.bangkit.sifresh.ui.activities.ProductDetailActivity
import academy.bangkit.sifresh.ui.adapter.ListProductAllAdapter
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var filteredProductList: List<Product> = ProductDataDummy.productList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example Image Banner Data (temporary)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://gust-production.s3.amazonaws.com/uploads/startup/panoramic_image/1007246/fruits-and-Vegetables-banner.jpg"))
        imageList.add(SlideModel("https://thumbs.dreamstime.com/b/background-food-fruits-vegetables-collection-fruit-vegetable-healthy-eating-diet-apples-oranges-banner-tomatoes-backgrounds-190445608.jpg"))
        imageList.add(SlideModel("https://warwick.ac.uk/fac/soc/economics/research/centres/cage/news/08-07-16-eating_more_fruit_and_vegetables_can_make_you_happier/banner.jpg"))

        // Image Banner Slider
        binding.imageSliderBanner.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSliderBanner.setSlideAnimation(AnimationTypes.ZOOM_OUT)

        // Category
        binding.rbCategoryAll.isChecked = true
        binding.rvMarketplaceItem.layoutManager = GridLayoutManager(requireActivity(), 2)
        if (binding.rbCategoryAll.isChecked) {
            val adapter = ListProductAllAdapter(ProductDataDummy.productList)
            binding.rvMarketplaceItem.adapter = adapter

            adapter.setOnItemClickCallback(object : ListProductAllAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Product) {
                    val intent = Intent(activity, ProductDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_category_all -> {
                    // Display all product
                    val adapter = ListProductAllAdapter(ProductDataDummy.productList)
                    binding.rvMarketplaceItem.adapter = adapter

                    adapter.setOnItemClickCallback(object :
                        ListProductAllAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Product) {
                            val intent = Intent(activity, ProductDetailActivity::class.java)
                            startActivity(intent)
                        }
                    })
                }
                R.id.rb_category_fruit -> {
                    // Display fruit product
                    val adapter = ListProductAllAdapter(ProductDataDummy.productFruit)
                    binding.rvMarketplaceItem.adapter = adapter

                    adapter.setOnItemClickCallback(object :
                        ListProductAllAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Product) {
                            val intent = Intent(activity, ProductDetailActivity::class.java)
                            startActivity(intent)
                        }
                    })
                }
                R.id.rb_category_vegetable -> {
                    // Display vegetable product
                    val adapter = ListProductAllAdapter(ProductDataDummy.productVegetable)
                    binding.rvMarketplaceItem.adapter = adapter

                    adapter.setOnItemClickCallback(object :
                        ListProductAllAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Product) {
                            val intent = Intent(activity, ProductDetailActivity::class.java)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchBar.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                /*
                 *  =============================================================
                 *  Query Search Product from viewModel to API
                 *  ex: viewModel.findUser(query)
                 *
                 *  ex ApiService:
                 *  @GET("/search/product")
                 *  fun searchProduct(@Query("q") login: String?): Call<Product>
                 *  =============================================================
                 */
                binding.searchBar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}