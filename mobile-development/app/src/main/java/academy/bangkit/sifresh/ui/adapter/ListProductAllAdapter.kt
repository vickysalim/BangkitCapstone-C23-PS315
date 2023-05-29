package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.Product
import academy.bangkit.sifresh.databinding.MarketplaceItemCardBinding
import android.view.LayoutInflater
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
        Glide.with(viewHolder.itemView.context)
            .load(product.productImageUrl)
            .into(viewHolder.binding.ivItemPhoto)
        viewHolder.binding.tvItemName.text = product.productName
        viewHolder.binding.tvItemPrice.text = product.productPrice

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listItem[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = listItem.size

    class ViewHolder(var binding: MarketplaceItemCardBinding) : RecyclerView.ViewHolder(binding.root)

}