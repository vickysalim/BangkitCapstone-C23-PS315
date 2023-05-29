package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.databinding.CartItemCardBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListItemCartAdapter(private val listItem: List<ProductCart>) :
    RecyclerView.Adapter<ListItemCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listItem[position]
        viewHolder.binding.tvItemName.text = item.product.productName
        Glide.with(viewHolder.itemView.context)
            .load(item.product.productImageUrl)
            .into(viewHolder.binding.ivItemPhoto)
        viewHolder.binding.tvItemPrice.text = item.product.productPrice
        viewHolder.binding.btnQuantityMin.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                viewHolder.binding.tvItemQuantity.text = item.quantity.toString()

                if (item.quantity == 0) {
                    viewHolder.binding.btnQuantityMin.isEnabled = false
                }
            }
        }
        viewHolder.binding.btnQuantityPlus.setOnClickListener {
            item.quantity++
            viewHolder.binding.tvItemQuantity.text = item.quantity.toString()
            viewHolder.binding.btnQuantityMin.isEnabled = true
        }
        viewHolder.binding.tvItemTotalPrice.text = item.product.productPrice
    }

    override fun getItemCount() = listItem.size

    class ViewHolder(var binding: CartItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}