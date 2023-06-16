package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.OrderListItem
import academy.bangkit.sifresh.databinding.CartItemCardBinding
import academy.bangkit.sifresh.utils.Helper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListItemOrderAdapter(private val listItem: List<OrderListItem>) :
    RecyclerView.Adapter<ListItemOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listItem[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = listItem.size

    inner class ViewHolder(var binding: CartItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderListItem) {
            with(binding) {
                tvItemName.text = item.productName
                Glide.with(itemView.context)
                    .load("https://storage.googleapis.com/sifresh-bucket-one/uploads/default.png")
                    .into(ivItemPhoto)
                tvItemPrice.text = Helper.formatCurrency(item.productPrice.toDouble())
                tvItemQuantity.text = item.amount.toString()
            }
        }
    }
}