package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.databinding.CartItemCardBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListItemCartAdapter(private val listItem: List<ProductCart>) : RecyclerView.Adapter<ListItemCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listItem[position]
        viewHolder.binding.tvItemName.text = item.product.productName
        Glide.with(viewHolder.itemView.context)
            .load(item.product.productImageUrl)
            .into(viewHolder.binding.ivItemPhoto)
        viewHolder.binding.tvItemPrice.text = item.product.productPrice
        viewHolder.binding.tvItemQuantity.text = item.quantity.toString()
    }

    override fun getItemCount() = listItem.size

    class ViewHolder(var binding: CartItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}