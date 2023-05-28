package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.OrderHistory
import academy.bangkit.sifresh.databinding.OrderHistoryCardBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val listOrder: List<OrderHistory>) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderHistoryCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val order = listOrder[position]
        viewHolder.binding.tvOrderId.text = order.orderId
        viewHolder.binding.tvOrderDate.text = order.orderDate
        viewHolder.binding.tvOrderTotalPrice.text = order.orderTotalPrice
        viewHolder.binding.tvOrderStatus.text = order.orderStatus
    }

    override fun getItemCount() = listOrder.size

    class ViewHolder (var binding: OrderHistoryCardBinding) : RecyclerView.ViewHolder(binding.root)
}