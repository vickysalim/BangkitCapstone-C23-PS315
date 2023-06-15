package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.CartItems
import academy.bangkit.sifresh.data.response.ProductCart
import academy.bangkit.sifresh.databinding.CartItemCardBinding
import academy.bangkit.sifresh.utils.Helper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListItemCartAdapter(private val listItem: List<CartItems>) :
    RecyclerView.Adapter<ListItemCartAdapter.ViewHolder>() {

    //private var totalPriceChangeListener: (() -> Unit)? = null

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

    inner class ViewHolder(var binding: CartItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItems) {
            with(binding) {
                tvItemName.text = item.productName
                Glide.with(itemView.context)
                    .load("https://storage.googleapis.com/sifresh-bucket-one/uploads/default.png")
                    .into(ivItemPhoto)
                tvItemPrice.text = Helper.formatCurrency(item.productPrice.toDouble())
                tvItemQuantity.text = item.amount.toString()
                /*tvItemQuantity.text = item.amount.toString()
                btnQuantityMin.setOnClickListener {
                    if (item.quantity > 0) {
                        item.quantity--
                        tvItemQuantity.text = item.quantity.toString()
                        notifyTotalPriceChangeListener()
                        updateItemTotalPrice(item)

                        if (item.quantity == 0) {
                            btnQuantityMin.isEnabled = false
                        }
                    }
                }
                btnQuantityPlus.setOnClickListener {
                    item.quantity++
                    tvItemQuantity.text = item.quantity.toString()
                    btnQuantityMin.isEnabled = true
                    notifyTotalPriceChangeListener()
                    updateItemTotalPrice(item)
                }
                updateItemTotalPrice(item)*/
            }
        }

/*        private fun updateItemTotalPrice(item: ProductCart){
            val itemTotalPrice = item.productDummy.productPrice * item.quantity
            binding.tvItemTotalPrice.text = Helper.formatCurrency(itemTotalPrice)
        }*/
    }

    /*fun setTotalPriceChangeListener(listener: () -> Unit) {
        totalPriceChangeListener = listener
    }

    private fun notifyTotalPriceChangeListener() {
        totalPriceChangeListener?.invoke()
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in listItem) {
            totalPrice += item.productDummy.productPrice * item.quantity
        }
        return totalPrice
    }*/
}