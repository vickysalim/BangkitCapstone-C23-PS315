package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.Seller
import academy.bangkit.sifresh.databinding.CartSectionBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListSellerCartAdapter(private val listSeller: List<Seller>) : RecyclerView.Adapter<ListSellerCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartSectionBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val seller = listSeller[position]
        viewHolder.binding.tvSellerName.text = seller.sellerName
        viewHolder.binding.rvCartItem.layoutManager = LinearLayoutManager(viewHolder.binding.root.context)
        viewHolder.binding.rvCartItem.adapter = ListItemCartAdapter(seller.productCart)
        viewHolder.binding.tvTotalPrice.text = seller.totalPrice
    }

    override fun getItemCount() = listSeller.size

    class ViewHolder(var binding: CartSectionBinding) : RecyclerView.ViewHolder(binding.root)
}