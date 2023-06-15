package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.ProductItem
import academy.bangkit.sifresh.databinding.MarketplaceItemCardBinding
import academy.bangkit.sifresh.ui.activities.ProductDetailActivity
import academy.bangkit.sifresh.utils.Helper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray

class ListProductAdapter :
    PagingDataAdapter<ProductItem, ListProductAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = MarketplaceItemCardBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = getItem(position)
        if (product != null) {
            viewHolder.bind(product)
        }
    }

    class ViewHolder(var binding: MarketplaceItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductItem) {
            with(binding) {
                val picUrl = JSONArray(product.productPicUrls).getString(0)
                Glide.with(itemView.context)
                    .load(picUrl)
                    .into(ivItemPhoto)
                tvItemName.text = product.name
                tvItemPrice.text = Helper.formatCurrency(product.price.toDouble())
                /*btnAddToCart.setOnClickListener {
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
                }*/
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ProductDetailActivity::class.java)
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_NAME,
                        product.name
                    )
                    val jsonImage = JSONArray(product.productPicUrls)
                    val imageList = ArrayList<String>()
                    for (i in 0 until jsonImage.length()) {
                        imageList.add(jsonImage.getString(i))
                    }
                    intent.putStringArrayListExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_IMAGE,
                        imageList
                    )
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_PRICE,
                        product.price.toDouble()
                    )
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_DESCRIPTION,
                        product.description
                    )
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_SELLER_NAME,
                        product.sellerName
                    )
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_SELLER_ID,
                        product.sellerId
                    )
                    intent.putExtra(
                        ProductDetailActivity.EXTRA_PRODUCT_ID,
                        product.id
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {
            override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}