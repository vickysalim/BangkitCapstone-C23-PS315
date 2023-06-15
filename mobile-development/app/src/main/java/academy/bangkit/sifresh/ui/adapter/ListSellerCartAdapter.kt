package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.CartItem
import academy.bangkit.sifresh.data.response.CartItems
import academy.bangkit.sifresh.data.response.CartSellerItem
import academy.bangkit.sifresh.data.response.Seller
import academy.bangkit.sifresh.databinding.CartSectionBinding
import academy.bangkit.sifresh.ui.activities.ConfirmOrderActivity
import academy.bangkit.sifresh.utils.Helper
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListSellerCartAdapter(private val listSeller: List<CartSellerItem>) :
    RecyclerView.Adapter<ListSellerCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartSectionBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val seller = listSeller[position]
        viewHolder.bind(seller)
    }

    override fun getItemCount() = listSeller.size

    class ViewHolder(var binding: CartSectionBinding) : RecyclerView.ViewHolder(binding.root) {
        private var listItemCartAdapter: ListItemCartAdapter? = null
        fun bind(seller: CartSellerItem) {
            with(binding) {
                tvSellerName.text = seller.fullName
                tvTotalPrice.text = Helper.formatCurrency(seller.totalPrice.toDouble())
                rvCartItem.layoutManager = LinearLayoutManager(root.context)
                listItemCartAdapter = ListItemCartAdapter(seller.products)
                rvCartItem.adapter = listItemCartAdapter

/*                listItemCartAdapter?.setTotalPriceChangeListener {
                    updateTotalPrice()
                }
                updateTotalPrice()*/

                btnAddToCart.setOnClickListener {
                    val intent = Intent(root.context, ConfirmOrderActivity::class.java)
                    intent.putExtra(SELLER, seller.sellerId)
                    intent.putExtra(SELLER_NAME, seller.fullName)
                    intent.putExtra(TOTAL_PRICE, seller.totalPrice)
                    root.context.startActivity(intent)
                }
            }
        }

/*        private fun updateTotalPrice() {
            val totalPrice = listItemCartAdapter?.getTotalPrice() ?: 0.0
            binding.tvTotalPrice.text = Helper.formatCurrency(totalPrice)
        }*/
    }

    companion object {
        public const val SELLER = "seller"
        public const val SELLER_NAME = "name"
        public const val TOTAL_PRICE = "price"
    }
}