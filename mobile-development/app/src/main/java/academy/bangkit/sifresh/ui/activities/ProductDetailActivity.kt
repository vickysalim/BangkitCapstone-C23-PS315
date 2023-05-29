package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.ProductReviewDummy
import academy.bangkit.sifresh.data.response.Review
import academy.bangkit.sifresh.databinding.ActivityProductDetailBinding
import academy.bangkit.sifresh.ui.adapter.ListProductReviewAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReviews.layoutManager = layoutManager

        setReviewAdapter(ProductReviewDummy.reviewList)
    }

    private fun setReviewAdapter(reviewList: List<Review>) {
        val adapter = ListProductReviewAdapter(reviewList)
        binding.rvReviews.adapter = adapter
    }
}