package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.Product
import academy.bangkit.sifresh.databinding.MarketplaceItemCardBinding
import academy.bangkit.sifresh.ui.activities.ProductDetailActivity
import academy.bangkit.sifresh.utils.Helper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListProductAllAdapter(private val listItem: List<Product>) : RecyclerView.Adapter<ListProductAllAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Product)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = MarketplaceItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = listItem[position]
        viewHolder.bind(product)
    }

    override fun getItemCount() = listItem.size

    class ViewHolder(var binding: MarketplaceItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (product: Product){
            with(binding) {
                Glide.with(itemView.context)
                    .load(product.productImageUrl)
                    .into(ivItemPhoto)
                tvItemName.text = product.productName
                tvItemPrice.text = Helper.formatCurrency(product.productPrice)
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
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ProductDetailActivity::class.java)
                    intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_NAME, product.productName)
                    intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_IMAGE, product.productImageUrl)
                    intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_PRICE, product.productPrice)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}