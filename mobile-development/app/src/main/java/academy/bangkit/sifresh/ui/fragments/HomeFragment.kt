package academy.bangkit.sifresh.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.ProductDataDummy
import academy.bangkit.sifresh.databinding.FragmentHomeBinding
import academy.bangkit.sifresh.ui.adapter.ListProductAllAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
        if (binding.rbCategoryAll.isChecked){
            binding.rvMarketplaceItem.layoutManager = GridLayoutManager(requireActivity(), 2)
            val adapter = ListProductAllAdapter(ProductDataDummy.productList)
            binding.rvMarketplaceItem.adapter = adapter
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.rb_category_all -> {
                    // Display all product
                    binding.rvMarketplaceItem.layoutManager = GridLayoutManager(requireActivity(), 2)
                    val adapter = ListProductAllAdapter(ProductDataDummy.productList)
                    binding.rvMarketplaceItem.adapter = adapter
                }
                R.id.rb_category_fruit -> {
                    // Display fruit product
                    binding.rvMarketplaceItem.layoutManager = GridLayoutManager(requireActivity(), 2)
                    val adapter = ListProductAllAdapter(ProductDataDummy.productFruit)
                    binding.rvMarketplaceItem.adapter = adapter
                }
                R.id.rb_category_vegetable -> {
                    // Display vegetable product
                    binding.rvMarketplaceItem.layoutManager = GridLayoutManager(requireActivity(), 2)
                    val adapter = ListProductAllAdapter(ProductDataDummy.productVegetable)
                    binding.rvMarketplaceItem.adapter = adapter
                }
            }
        }
    }
}