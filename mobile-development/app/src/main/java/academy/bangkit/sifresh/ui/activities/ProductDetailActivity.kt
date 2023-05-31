package academy.bangkit.sifresh.ui.activities

import academy.bangkit.sifresh.data.ProductReviewDummy
import academy.bangkit.sifresh.data.response.Review
import academy.bangkit.sifresh.databinding.ActivityProductDetailBinding
import academy.bangkit.sifresh.ui.adapter.ListProductReviewAdapter
import academy.bangkit.sifresh.utils.Helper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvProductName.text = intent.getStringExtra(EXTRA_PRODUCT_NAME)
            Glide.with(root)
                .load(intent.getStringExtra(EXTRA_PRODUCT_IMAGE))
                .into(imgBanner)
            tvProductPrice.text = Helper.formatCurrency(intent.getDoubleExtra(EXTRA_PRODUCT_PRICE, 0.0),)
            btnAddToCart.setOnClickListener {
                btnAddToCart.visibility = View.GONE
                viewQuantityCount.visibility = View.VISIBLE
                tvItemQuantity.text = "1"

                btnQuantityMin.setOnClickListener {
                    val quantity = tvItemQuantity.text.toString().toInt()
                    val newQuantity = quantity - 1
                    if (newQuantity > 1) {
                        tvItemQuantity.text = newQuantity.toString()
                    } else {
                        btnAddToCart.visibility = View.VISIBLE
                        viewQuantityCount.visibility = View.GONE
                    }
                }

                btnQuantityPlus.setOnClickListener {
                    val quantity = tvItemQuantity.text.toString().toInt()
                    val newQuantity = quantity + 1
                    tvItemQuantity.text = newQuantity.toString()
                }
            }
            btnBack.setOnClickListener {
                finish()
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvReviews.layoutManager = layoutManager
        setReviewAdapter(ProductReviewDummy.reviewList)
    }

    private fun setReviewAdapter(reviewList: List<Review>) {
        val adapter = ListProductReviewAdapter(reviewList)
        binding.rvReviews.adapter = adapter
    }

    companion object {
        const val EXTRA_PRODUCT_NAME = "extra_product_name"
        const val EXTRA_PRODUCT_IMAGE = "extra_product_image"
        const val EXTRA_PRODUCT_PRICE = "extra_product_price"
    }
}